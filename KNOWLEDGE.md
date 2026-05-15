# Spring Boot Learning Notes

A living document to capture concepts, observations, and useful references as I work through this Spring Boot project.

## What is Spring Boot?

Spring Boot is a framework that simplifies building backend applications and APIs. It ships with a built-in web server (Tomcat by default), so you can run the backend locally without needing to set up or connect to an external server manually.

**Dependency Injection:** Rather than creating objects by hand, Spring automatically manages them for you using annotations. The table below covers the most common ones:

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

Gradle is a build tool that handles pulling in and managing the external libraries your project depends on, such as:

- Spring MVC
- Tomcat
- JSON support
- Web infrastructure

## What is a Bean?
A bean is a managed object.

A Bean annotation tells Spring how to create the object it is attached to. This allows Spring to better create and manage your objects. 

