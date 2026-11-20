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
```

## Build, test, and run with Maven

From the project root:

### Build

```powershell
mvn clean package
```

This compiles the application and runs the tests.

### Test

```powershell
mvn test
```

### Run

Start the SAAB server first, then run:

```powershell
mvn exec:java
```

## Assumptions

- The SAAB server is available locally and listens on `localhost:5463`.
- The server sends one object update per line using semicolon-separated fields.
- Object IDs remain stable while an object's position changes.
- X and Y coordinates are interpreted as pixel coordinates relative to `map.gif`.
- Coordinates outside the image remain stored and may not be visibly drawn.
- Object IDs are expected to be non-negative.
- Object types are limited to `1`, `2`, and `3`.
- Old object remains when a reconnect occurs.

## Implementation decisions

- Maven is used for compilation, testing, execution, and packaging.
- The map is packaged in `src/main/resources/map.gif` and loaded through the classpath.
- TCP input is parsed and converted into immutable `MapObjectUpdate` records.
- Objects are stored in a map keyed by their object ID.
- The TCP client reconnects automatically after connection loss.
- Reconnection uses exponential backoff from one second up to thirty seconds.
- The backoff resets after a successful connection.
- Host and port settings are read from `client.properties`.
- Missing or invalid client configuration falls back to `localhost:5463`.
- Malformed TCP updates are ignored rather than terminating the application.

