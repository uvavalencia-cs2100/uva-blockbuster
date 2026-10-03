# Architecture guide

How the code is organised, and what happens when the app starts. The diagrams are
[Mermaid](https://mermaid.js.org/), so GitHub and VS Code (with a Mermaid extension) draw them.

- [1. Entity, Service, Repository](#1-entity-service-repository)
- [2. What happens at boot](#2-what-happens-at-boot)
- [3. UML: the entities](#3-uml-the-entities)
- [4. UML: Entity → Service → Repository (Customer)](#4-uml-entity--service--repository-customer)
- [5. Adding a new entity](#5-adding-a-new-entity)

## 1. Entity, Service, Repository

The data of the app is split in three layers. Each one has one job and only talks to the layer
next to it.

| Layer | Job | Knows about | Does **not** know about |
|---|---|---|---|
| **Entity** | One thing of the real world (a customer, a movie), and what makes it valid | its own fields and rules | collections, files, other entities' storage |
| **Service** | The *collection* of an entity and the rules about the collection (unique ids, add, find, remove) | the entity, the repository (as a black box) | file paths, CSV, how data is stored |
| **Repository** | Storing and reading entities, i.e. the only place that knows *how* they are stored | the entity, the file format | business rules, menus, the UI |

```text
 menus / commands  ──►  Service  ──►  Repository  ──►  customers.csv
                          │               │
                          └──── Entity ◄──┘
```

**Entity.** Identified by its id and never allowed to exist in an invalid state: the constructor
and the setters throw `IllegalArgumentException` for bad values, so a `Customer` you are holding
is always valid. It also knows how to turn itself into a CSV line and back
(`Customer.toCSVLine` / `Customer.fromCSVLine`), because only the class knows its own columns.

**Service.** Owns the in-memory collection (a `Map` by id) and the rules about it, such as "two
customers cannot share an id". Commands and menus only talk to the service. After every change
(`add`, `remove`) the service hands the whole list to the repository, so callers never have to
remember to save.

**Repository.** Reads and writes the CSV file: the folder, the file name, the header, skipping
bad lines, and writing through a temporary file so a failure never leaves the file half written.
If we moved to a database tomorrow, only this class would change.

Why bother? Each class has one reason to change, you can read one layer without understanding the
others, and a bug has an obvious place to live: *wrong data* → entity, *wrong rules* → service,
*wrong file* → repository.

Status today: **Customer** has all three layers. **Movie** and **MovieCopy** are entities only;
they get a service and a repository when they are ready (see [section 5](#5-adding-a-new-entity)).

## 2. What happens at boot

Everything before the first screen appears, in order. It starts in [Main](../src/main/java/Main.java):

```java
LogBuffer.install();                                       // 1
log.info("Welcome to Blockbuster!");                       // 2
AppContext.getInstance().setConfig(AppConfig.load(args));  // 3
AppContext.getInstance().loadData();                       // 4
Screen.start();                                            // 5 (UI, not covered here)
new MenuOptions().runMenu();                               // 6 (UI, not covered here)
```

1. **Logging** is redirected into an in-memory buffer (`LogBuffer`), so everything logged from
   here on can be shown in the log frame instead of the console.
2. A **welcome** line is logged.
3. **Configuration** is read by `AppConfig.load(args)` and given to the `AppContext`.
   - The file is the first program argument, or `config.properties` in the current folder.
   - A missing file, an empty file, or a missing or blank property is fine: the default is used.
   - What is read is logged, so you can see which file and values were used.
4. **Data** is loaded by `AppContext.loadData()` (details below).

| Property | Default | Meaning |
|---|---|---|
| `data_path` | `data` | Folder holding the CSV files. Loading and saving both use it, so they always agree. |

### The Singleton pattern

A **singleton** is a class that has exactly **one instance** in the whole program, and gives
everyone the same one. You can't create it with `new`; you ask the class for it.

```java
public class AppContext {
    private static final AppContext INSTANCE = new AppContext();   // the one and only instance

    private AppContext() { }                                        // private: nobody else can call `new`

    public static AppContext getInstance() {                        // everyone gets the same object
        return INSTANCE;
    }
}
```

Three ingredients: a **private constructor**, a **static field** holding the single instance, and
a **static method** that returns it.

Why use it here: the customers must exist **once**. If the menus and the code that loads the
data each created their own `CustomerService`, they would hold different lists and a customer
added in one place would not exist in the other. With a singleton, `AppContext.getInstance()`
returns the same object everywhere.

Its cost: it is global state, which any class can reach. Code that quietly depends on a global is
harder to test (you can't easily swap in a fake) and its dependencies are hidden. That is why
only the app's shared things live in it, and why the layers below it (`CustomerService`,
`CustomerRepository`) are plain classes that receive what they need through their constructor,
not singletons.

### The AppContext

[AppContext](../src/main/java/app/AppContext.java) is a *singleton*: one instance for the whole
program, reached with `AppContext.getInstance()`. It is the one place where the shared things
live, so every part of the app talks to the **same** `CustomerService` and reads the **same**
configuration. It holds:

- the `AppConfig` (starts with the defaults until `setConfig` replaces it), and
- the `CustomerService`.

The service is **not** built when `AppContext` is created. It is built on the first
`getCustomerService()` call, because it needs the final config (where the data folder is) and
building it also loads the customers. That is why `Main` sets the config *before* loading data.
Calling `setConfig` again discards the service; the next `getCustomerService()` builds a new one
from the new location.

### Loading the customers

```mermaid
sequenceDiagram
    participant Main
    participant Config as AppConfig
    participant Ctx as AppContext
    participant Svc as CustomerService
    participant Repo as CustomerRepository
    participant File as customers.csv

    Main->>Config: load(args)
    Config-->>Main: AppConfig (data_path)
    Main->>Ctx: setConfig(config)
    Main->>Ctx: loadData()
    Ctx->>Ctx: log "Loading data from <data_path>"
    Ctx->>Ctx: getCustomerService() (first call)
    Ctx->>Repo: new CustomerRepository(data_path)
    Ctx->>Svc: new CustomerService(repository)
    Svc->>Repo: load()
    Repo->>File: read all lines
    File-->>Repo: header + lines
    loop each line after the header
        Repo->>Repo: Customer.fromCSVLine(line)
    end
    Repo-->>Svc: List of Customer
    loop each customer
        Svc->>Svc: put(customer)
    end
    Svc-->>Ctx: ready, with every stored customer
```

Two rules make this safe:

- The service **loads in its constructor**, so a `CustomerService` that exists is always loaded.
  Otherwise the first `add` after a forgotten `load()` would save a file with only the new
  customer and overwrite the stored ones.
- Loading never saves back. Only `add` and `remove` do.

A bad line (wrong number of columns, an id that is not a number, a name that is blank, …) never
stops the app: it is logged as a warning and skipped. A missing file is logged as an error and
the app starts with no customers.

## 3. UML: the entities

```mermaid
classDiagram
    class Customer
    class Movie
    class MovieCopy
    class Status {
        <<enumeration>>
    }

    MovieCopy "*" --> "1" Movie : is a copy of
    MovieCopy --> Status : has
```

Notes:

- **Customer** is the finished entity. Its id never changes (it is `final`), `equals`/`hashCode`
  use only the id (two customers are the same customer if they share an id), and the setters
  validate. Names and emails cannot contain a comma, because that would corrupt the CSV.
- **Movie** and **MovieCopy** are still being built: they have no id yet and no service or
  repository. A `Movie` is the film; each physical/rentable item is a `MovieCopy` that points to
  its `Movie` and has a `Status`.

## 4. UML: Entity → Service → Repository (Customer)

```mermaid
classDiagram
    direction LR

    class CustomerCommands {
        <<commands>>
    }
    class AppContext {
        <<singleton>>
    }
    class AppConfig
    class CustomerService {
        <<service>>
    }
    class CustomerRepository {
        <<repository>>
    }
    class Customer {
        <<entity>>
    }
    class customers_csv["customers.csv"] {
        <<file>>
    }

    CustomerCommands ..> AppContext : getCustomerService()
    AppContext *-- AppConfig
    AppContext *-- CustomerService : creates on first use
    AppContext ..> CustomerRepository : creates from data_path
    CustomerService --> CustomerRepository : load / save
    CustomerService o-- "*" Customer
    CustomerRepository ..> Customer : reads and writes
    CustomerRepository --> customers_csv
```

Reading it left to right: the commands ask the `AppContext` for the service; the service keeps
the customers and the rules; the repository turns them into lines of `customers.csv` and back,
using the `Customer` methods that know the columns.

What happens when the user adds a customer:

```mermaid
sequenceDiagram
    actor User
    participant Cmd as CustomerCommands
    participant Svc as CustomerService
    participant Cust as Customer
    participant Repo as CustomerRepository
    participant File as customers.csv

    User->>Cmd: Id, Name, Email
    Cmd->>Cust: new Customer(id, name, email)
    Cmd->>Svc: add(customer)
    Svc->>Svc: put(customer)
    Svc->>Repo: save(list of all customers)
    Repo->>Cust: toCSVLine(customer) for each
    Repo->>File: write customers.csv
    Cmd-->>User: "Customer added" (log frame)
```

Each layer rejects what it is responsible for: the **entity** rejects invalid values, the
**service** rejects duplicates, and the **repository** is the only one that can fail on the disk
(that is logged as an error and the change stays in memory).

## 5. Adding a new entity

For example, Movie, once it is ready:

1. **Entity**: a validated class with an id, `fromCSVLine` and `toCSVLine`, and a `CSV_HEADER`.
2. **Repository**: `MovieRepository(String dataPath)` with `load()` and `save(List<Movie>)`.
3. **Service**: `MovieService(MovieRepository)`; load in the constructor, save after each change.
4. **AppContext**: a `getMovieService()` built on first use, like the customer one, and one more
   line in `loadData()` so it loads at boot.
5. **Commands**: menus call the service through `AppContext`; they never touch the repository.
