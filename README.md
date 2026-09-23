# uva-blockbuster

A simple, console-based Blockbuster-style video rental app, built as a hands-on
Java exercise. Everything runs in memory — there's no database.

## Requirements

- JDK 21 or newer
- Maven:
  - macOS: [`brew install maven`](https://formulae.brew.sh/formula/maven)
  - Windows: [Chocolatey](https://chocolatey.org/packages/maven): `choco install maven`
    (there's no official winget package yet; see the
    [manual install instructions](https://maven.apache.org/install.html) if you'd
    rather not use Chocolatey)

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
