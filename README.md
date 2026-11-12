# SaabMap

A Java Swing application that visualizes objects received in real time from the
provided SAAB TCP server.

## Requirements

- Java 17 or newer
- Maven
- The provided `SAAB.jar`

## Project structure

- `src/main/java` – application source code
- `src/main/resources/map.gif` – map image
- `src/test/java` – automated tests
- `pom.xml` – Maven build configuration

## Starting the server

The SAAB server must be started separately.

Create a server directory containing:

- `SAAB.jar`
- `server.properties`
- `map.gif`

Example `server.properties`:

```properties
SERVERPORT=5463
MAP=map.gif