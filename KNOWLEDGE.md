# Spring Boot Learning Notes

A living document to capture concepts, observations, and useful references as I work through this Spring Boot project.

## What is Spring Boot?

Spring Boot is a framework that simplifies building backend applications and APIs. It ships with a built-in web server (Tomcat by default), so you can run the backend locally without needing to set up or connect to an external server manually.

**Dependency Injection:** Rather than creating objects by hand with `new MyClass()`, Spring automatically creates and manages them for you using annotations. This means you never have to worry about wiring things together manually. The table below covers the most common annotations:

**Dependency Injection:** Instead of manually creating objects, Spring manages the class using annotations.

| Annotation               | Purpose                |
| ------------------------ | ---------------------- |
| `@SpringBootApplication` | starts app             |
| `@RestController`        | API controller         |
| `@GetMapping`            | HTTP GET endpoint      |
| `@PostMapping`           | HTTP POST endpoint     |
| `@Service`               | business logic         |
| `@Repository`            | DB layer               |
| `@Autowired`             | inject dependency      |
| `@Entity`                | database table mapping |

## What is Gradle?

Gradle is a build tool that handles pulling in and managing the external libraries your project depends on. Instead of manually downloading `.jar` files, you declare what you need and Gradle fetches it, such as:

- Spring MVC
- Tomcat
- JSON support
- Web infrastructure

## What is a Bean?

A bean is an object that Spring creates and manages for you. When you annotate a method with `@Bean`, you're telling Spring: "call this method at startup and store whatever it returns." Spring keeps all beans in a central container called the `ApplicationContext`, and can hand them out wherever they're needed in your app.
