package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// annotation tells Spring Boot that this class controls incoming web requests
@RestController
public class HelloController {
    // handles specific URL path "/" for a GET request
    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}