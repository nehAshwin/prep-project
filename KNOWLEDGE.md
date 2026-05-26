# Spring Boot Learning Notes

A living document to capture concepts, observations, and useful references as I work through this Spring Boot project.

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

A **domain model** is a plain Java class that represents a business concept. 

One example of this can be our `Device.java`, which represents devices.

It is not a controller and usually has no web annotations. It's fields are typically **private**. It uses **encapsulation**, which means other code reads or changes that domain's state through methods (getters/setters), not by touching fields directly.

### JSON serialization

Despite using Java objects entirely, we can still cleanly return information in JSON format, thanks to **Spring MVC**.  When a controller method returns a Java object or collection of objects, Spring MVC uses **Jackson** (bundled with `spring-boot-starter-web`) to **serialize** it, converting it into **JSON** for the HTTP response body. 

**Serialization**: turning in-memory data into a transferable format the client can parse.

Jackson maps object properties to JSON keys (e.g. a `getId()` getter often becomes `"id"` in JSON). The client (browser, Postman, another service) never receives Java objects over the wire, only JSON text.

---

## 4. Web layer

A **REST API** exposes endpoints that return data (often JSON) instead of HTML pages. `**@RestController`** marks a class that handles HTTP requests; method return values are sent directly as the response body (then serialized by Jackson when the return type is an object or collection).

### Request flow

HTTP request → Tomcat → DispatcherServlet → controller method → Java object(s) → Jackson → JSON response

As the app grows, logic often splits across layers:

HTTP request → controller → service → repository → database

### Web annotations


| Annotation        | Purpose                                                        |
| ----------------- | -------------------------------------------------------------- |
| `@RestController` | Class handles API / web requests                               |
| `@GetMapping`     | Maps a method to an HTTP GET request                           |
| `@PostMapping`    | Maps a method to an HTTP POST request                          |
| `@Service`        | Business logic layer                                           |
| `@Repository`     | Database access layer                                          |
| `@Entity`         | Maps a Java class to a database table                          |
| `@Autowired`      | Inject a Spring-managed dependency into a field or constructor |


---

## 5. Testing

Tests verify behavior. **Unit tests** focus on one class in isolation. **Integration tests** load multiple parts of the app together — often the full Spring context with `@SpringBootTest`.

### Common test annotations


| Annotation                     | Read by | Purpose                                                        |
| ------------------------------ | ------- | -------------------------------------------------------------- |
| `@Test`                        | JUnit   | Mark a method as a test to run when you execute the test suite |
| `@SpringBootTest`              | Spring  | Load the Spring application context for the test               |
| `@Autowired`                   | Spring  | Inject a test helper or dependency Spring already created      |
| `@AutoConfigureMockMvc`        | Spring  | Provide an in-process fake HTTP client (`MockMvc`)             |
| `@AutoConfigureRestTestClient` | Spring  | Provide `RestTestClient` for HTTP-style calls in tests         |


`@Autowired` in tests works the same way as in application code: Spring supplies a ready-made object; you do not construct it yourself.

### MockMvc vs RestTestClient


| Approach                             | How it works                                                | When to use                                            |
| ------------------------------------ | ----------------------------------------------------------- | ------------------------------------------------------ |
| **MockMvc**                          | Simulated HTTP inside the JVM; usually no real network port | Fast controller tests; good default for many web tests |
| **RestTestClient** (+ `RANDOM_PORT`) | Starts embedded server on a random port; sends real HTTP    | Closer to production; exercises the full HTTP stack    |


Both can assert status codes and response bodies; they differ in how realistic and how heavy the setup is.

---

## 6. Quick reference — annotations


| Annotation               | Purpose                                       |
| ------------------------ | --------------------------------------------- |
| `@SpringBootApplication` | App entry point; enables auto-config and scan |
| `@Bean`                  | Register method return value as a Spring bean |
| `@RestController`        | API controller                                |
| `@GetMapping`            | HTTP GET endpoint                             |
| `@PostMapping`           | HTTP POST endpoint                            |
| `@Service`               | Business logic                                |
| `@Repository`            | Database layer                                |
| `@Autowired`             | Inject a dependency                           |
| `@Entity`                | Database table mapping                        |
| `@Test`                  | JUnit: run method as a test                   |
| `@SpringBootTest`        | Load Spring context in tests                  |


