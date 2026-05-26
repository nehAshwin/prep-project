# IoT Device Management Backend

Spring Boot learning project — a minimal REST API that will grow into an IoT device management backend. For conceptual notes (annotations, testing, architecture), see [KNOWLEDGE.md](KNOWLEDGE.md).

## Prerequisites

- **Java 17** (configured in `build.gradle`)
- No separate server install — Spring Boot runs an embedded Tomcat

## Run the app

```bash
./gradlew bootRun
```

The server starts on **http://localhost:8080** by default.

### Try the API

```bash
curl http://localhost:8080/
```

Expected response:

```
Greetings from Spring Boot!
```

## Run tests

```bash
./gradlew test
```

Tests cover the root `GET /` endpoint using two styles:

- **MockMvc** — simulated HTTP in-process (fast)
- **RestTestClient** — real HTTP against an embedded server on a random port

## Useful Gradle commands

| Command | Description |
| ------- | ----------- |
| `./gradlew bootRun` | Start the application |
| `./gradlew test` | Run all tests |
| `./gradlew build` | Compile, test, and package the app |

## Project layout (high level)

```
src/main/java/     Application entry point and REST controllers
src/main/resources/  Configuration (e.g. application.properties)
src/test/java/       Controller and integration tests
```

## Stack

- Spring Boot 4.x
- Spring Web (`spring-boot-starter-web`)
- Gradle
- JUnit 5
