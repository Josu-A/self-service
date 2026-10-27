# Self Service

Concurrent Java simulation of a self-service restaurant (customers, staff and a
cook sharing a `SelfService` monitor). Uses a Swing GUI by default.

## Requirements

- JDK 11+
- A graphical display (the GUI is Swing)

## Build

From the project root:

```bash
javac *.java
jar cfe Praktika-SelfService.jar SelfServiceApp *.class images
```

## Run

```bash
java -jar Praktika-SelfService.jar
```

## Text-only mode

In `SelfServiceApp.main`, replace `new PantailaGUI()` with `new PantailaCLI()` and
rebuild.
