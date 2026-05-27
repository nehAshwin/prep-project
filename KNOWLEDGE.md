# Spring Boot Learning Notes

Concept-focused notes on Spring Boot. Add depth under each section as you learn more.

## Table of contents

1. [Foundations](#1-foundations) — Spring Boot, dependency injection, beans
2. [Build and dependencies](#2-build-and-dependencies) — Gradle, starters
3. [Domain models and JSON](#3-domain-models-and-json-serialization) — models, encapsulation, Jackson
4. [Web layer](#4-web-layer) — REST, CRUD, request flow, path variables, SRP
5. [Testing](#5-testing) — unit vs integration, MockMvc vs RestTestClient
6. [Quick reference](#6-quick-reference--annotations) — grouped annotation cheat sheet

---

## 1. Foundations

### What is Spring Boot?

Spring Boot is a framework that simplifies building backend applications and APIs.

Additionally, it has a built in web server using Tomcat so you can run the backend locally without setting up an external server.

### Dependency injection (Object Management)

Rather than creating every new object with `new MyClass()`, Spring creates and manages them for you. You declare what you need, and Spring wires dependencies together. 

#### Example:

Suppose you have a service:

```java
@Service
public class GreetingService {
    public String getGreeting() { return "Hello!"; }
}
```

And a controller that uses it:

```java
@RestController
public class GreetingController {
    private final GreetingService greetingService;

    // Spring automatically injects GreetingService here
    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("/greet")
    public String greet() {
        return greetingService.getGreeting();
    }
}
```

You do **not** need to create a `GreetingService` object and pass it into `GreetingController` yourself. Spring finds it using the `@Service` annotation, makes a single instance, and passes it to `GreetingController` automatically.

This is also called **inversion of control** — the framework controls object creation, not your application code.

### Beans and ApplicationContext

A **bean** is an object that Spring creates and manages. When you annotate a method with `@Bean`, you tell Spring to call that method at startup and store the result. Spring keeps all beans in a central container called the **ApplicationContext** and can inject them wherever they are needed.

Beans are especially useful in large systems with many objects and dependencies. Having them managed reduces wiring mistakes and makes components easier to test and replace.

---

## 2. Build and dependencies

### Gradle

Gradle is a build tool that declares and downloads external libraries (dependencies). You list what you need; Gradle fetches compatible versions instead of you manually managing `.jar` files.

### Starters

**Starters** are dependency bundles for common setups. For example:

- `spring-boot-starter` — core Spring Boot only (no web stack)
- `spring-boot-starter-web` — web stack (Spring MVC, embedded Tomcat, JSON support)
- `spring-boot-starter-test` — testing libraries (JUnit, MockMvc, Spring Test)

Choosing the right starter determines which auto-configuration and APIs are available.

---

## 3. Domain models and JSON Serialization

### Domain model

A **domain model** is a plain Java class that represents a business concept (e.g. a device, user, or order). It is not a controller and usually has no web annotations. Fields are typically **private**; **encapsulation** means other code reads or changes state through methods (getters/setters), not by touching fields directly.

A **no-args constructor** is often needed so Jackson can create an empty object when **deserializing** JSON from a request body, then set fields via setters.

### JSON serialization

Despite using Java objects entirely, we can still cleanly return information in JSON format, thanks to **Spring MVC**.  When a controller method returns a Java object or collection of objects, Spring MVC uses **Jackson** (bundled with `spring-boot-starter-web`) to **serialize** it, converting it into **JSON** for the HTTP response body. 

**Serialization**: turning in-memory data into a transferable format the client can parse.

Jackson maps object properties to JSON keys (e.g. a `getId()` getter often becomes `"id"` in JSON). The client (browser, Postman, another service) never receives Java objects over the wire, only JSON text.

**Deserialization** is the reverse: JSON in an HTTP request body → Java object. Spring uses Jackson plus `@RequestBody` on a controller parameter to build that object automatically.

---

## 4. Web layer

A **REST API** exposes endpoints that return data (often JSON) instead of HTML pages. **`@RestController`** marks a class that handles HTTP requests; method return values are sent directly as the response body (then serialized by Jackson when the return type is an object or collection).

### CRUD operations

**CRUD** stands for the four basic operations on stored data:

| Operation | Meaning | Typical HTTP method | Typical path pattern |
| --------- | ------- | ------------------- | -------------------- |
| **C**reate | Add a new record | `POST` | `POST /resources` |
| **R**ead | Fetch one or many records | `GET` | `GET /resources`, `GET /resources/{id}` |
| **U**pdate | Change an existing record | `PUT` (full replace) or `PATCH` (partial) | `PUT /resources/{id}` |
| **D**elete | Remove a record | `DELETE` | `DELETE /resources/{id}` |

In a layered app, the **controller** maps HTTP to Java calls; the **service** performs create/read/update/delete logic on in-memory or database data.

**Example flow (create):** client sends JSON in the body → `@PostMapping` + `@RequestBody` → controller calls service → service stores object → controller returns created object → Jackson → JSON response.

**Example flow (read one):** client requests `GET /resources/{id}` → `@PathVariable` supplies `id` → service finds record → controller returns it or `null` / error handling.

Use **nouns** in URLs (`/devices`, not `/getDevices`). The HTTP method expresses the action.

### Request flow

HTTP request → Tomcat → DispatcherServlet → controller method → Java object(s) → Jackson → JSON response

As the app grows, logic often splits across layers:

HTTP request → controller → service → repository → database

### Path variables

A **path variable** is a dynamic segment in a URL. Instead of writing a separate endpoint for every device, you define a placeholder in the path with `{name}` and annotate the method parameter with `@PathVariable`.

For example, `@GetMapping("/resources/{id}")` matches `/resources/1`, `/resources/2`, etc. Spring extracts the value from the URL and passes it to the method parameter.

### Request body (`@RequestBody`)

For **POST** and **PUT**, the client often sends JSON in the **request body**. Annotate a method parameter with `@RequestBody` so Spring deserializes that JSON into a Java object (e.g. your domain model). The controller then passes that object to the service.

### Single Responsibility Principle (SRP)

Each layer in the request flow should have **one primary job**:

| Layer          | Responsibility                                      |
| -------------- | --------------------------------------------------- |
| **Controller** | Accept HTTP requests, delegate work, return responses |
| **Service**    | Business logic and data manipulation                |
| **Repository** | Database access (when added)                        |
| **Model**      | Represent domain data (fields, getters/setters)     |

Controllers should **not** contain business logic or data. Services should **not** know about HTTP or JSON. Keeping responsibilities separated makes each layer easier to understand, test, and change independently.

See [§6 Quick reference](#6-quick-reference--annotations) for web and layer annotations.

---

## 5. Testing

Tests verify behavior. **Unit tests** focus on one class in isolation. **Integration tests** load multiple parts of the app together — often the full Spring context with `@SpringBootTest`.

`@Autowired` in tests works the same way as in application code: Spring supplies a ready-made object; you do not construct it yourself. See [§6 Testing annotations](#testing).

### MockMvc vs RestTestClient


| Approach                             | How it works                                                | When to use                                            |
| ------------------------------------ | ----------------------------------------------------------- | ------------------------------------------------------ |
| **MockMvc**                          | Simulated HTTP inside the JVM; usually no real network port | Fast controller tests; good default for many web tests |
| **RestTestClient** (+ `RANDOM_PORT`) | Starts embedded server on a random port; sends real HTTP    | Closer to production; exercises the full HTTP stack    |


Both can assert status codes and response bodies; they differ in how realistic and how heavy the setup is.

---

## 6. Quick reference — annotations

Grouped cheat sheet. Concept explanations live in earlier sections.

### Application

| Annotation               | Purpose                                       |
| ------------------------ | --------------------------------------------- |
| `@SpringBootApplication` | App entry point; enables auto-config and scan |
| `@Bean`                  | Register method return value as a Spring bean |

### Dependency injection

| Annotation    | Purpose                                                     |
| ------------- | ----------------------------------------------------------- |
| `@Autowired`  | Inject a Spring-managed dependency (field or constructor)   |
| `@Service`    | Mark class as business logic layer (Spring-managed bean)    |
| `@Repository` | Mark class as database access layer (Spring-managed bean)  |

### Web (REST / CRUD)

| Annotation        | Purpose                                         |
| ----------------- | ----------------------------------------------- |
| `@RestController` | Class handles API / web requests                |
| `@GetMapping`     | HTTP GET (Read)                                 |
| `@PostMapping`    | HTTP POST (Create)                              |
| `@PutMapping`     | HTTP PUT (Update)                               |
| `@DeleteMapping`  | HTTP DELETE (Delete)                            |
| `@PathVariable`   | Inject URL path segment as method parameter     |
| `@RequestBody`    | Deserialize JSON request body → Java object     |

### Data (persistence)

| Annotation | Purpose                              |
| ---------- | ------------------------------------ |
| `@Entity`  | Map a Java class to a database table |

### Testing

| Annotation                     | Read by | Purpose                                                        |
| ------------------------------ | ------- | -------------------------------------------------------------- |
| `@Test`                        | JUnit   | Mark a method as a test                                        |
| `@SpringBootTest`              | Spring  | Load the Spring application context for the test               |
| `@AutoConfigureMockMvc`        | Spring  | Provide in-process fake HTTP client (`MockMvc`)                |
| `@AutoConfigureRestTestClient` | Spring  | Provide `RestTestClient` for HTTP-style calls in tests         |

`@Autowired` in tests uses the same annotation as in application code (see Dependency injection).

