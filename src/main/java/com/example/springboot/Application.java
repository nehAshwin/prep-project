// package: folder/namespace to organize Java classes
package com.example.springboot;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

// annotation tells Spring Boot to start application from here
// to enable auto-configuration based on the libraries?
// and to scan the package for other classes (ex: HelloController)
@SpringBootApplication
/*
public: accessible from other classes
class: defines new class named Application
*/
public class Application {
    /*
    static: method associated with class, object not needed
    void: returns nothing
    main: standard entry point for Java applications
    String[]: function argument is a string list named args
    */
    public static void main(String[] args) {
        // boots up Spring framework
        SpringApplication.run(Application.class, args);
    }

    // Bean: managed object
    // Bean annotation tells Spring that the object output should become a Spring bean
    // Spring will store return output in ApplicationContext
    @Bean
    //                                         master container -> stores beans, etc. 
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("Let's inspect the beans provided by Spring Boot: ");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
        };
    }
}