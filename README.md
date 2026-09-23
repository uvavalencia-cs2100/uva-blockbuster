# uva-blockbuster

A simple, console-based Blockbuster-style video rental app, built as a hands-on
Java exercise. Everything runs in memory — there's no database.

## Requirements

- JDK 21 or newer
- Maven:
  - macOS: [`brew install maven`](https://formulae.brew.sh/formula/maven)
  - Windows: no official winget package exists yet, and Chocolatey (`choco`)
    isn't installed by default, so use the
    [official manual install](https://maven.apache.org/install.html):
    1. Download the binary zip from the
       [Maven downloads page](https://maven.apache.org/download.cgi) and
       extract it, e.g. to `C:\Program Files\Apache\maven`.
    2. Add its `bin` folder to your `PATH` environment variable.
    3. Open a new terminal and confirm with `mvn -v`.

    If you already have [Chocolatey](https://chocolatey.org/install) set up,
    `choco install maven` works too.

## Building

```sh
mvn compile     # just compile
mvn package     # compile and produce a runnable jar in target/
```

## Running

```sh
java -jar target/uva-blockbuster.jar
```

The jar's manifest ([src/main/resources/META-INF/MANIFEST.MF](src/main/resources/META-INF/MANIFEST.MF))
points at `Main`, so no extra `-cp`/main-class flags are needed.

While developing, you can also run straight from compiled classes without
packaging a jar:

```sh
mvn compile
java -cp target/classes Main
```

## CI

Every pull request runs `.github/workflows/java-build.yml`, which compiles the
project with Maven and fails on any compiler warning (`-Werror`-equivalent via
`maven.compiler.failOnWarning`). Pushes to `main` also run
`.github/workflows/merge.yaml`, which packages the jar and uploads it as a
workflow artifact.

## Contributing

- Branch off `main` as `<your-name>/<short-description>`.
- Open a pull request; CI must pass before merging.
- Check the [issue tracker](https://github.com/uvavalencia-cs2100/uva-blockbuster/issues)
  for open tasks.
