/*
Tests the HelloController GET Endpoint
starts the app on a real RANDOM_PORT port
uses RestTestClient to send an HTTP request like a real client
tests more similar to production
*/

package com/example.springboot;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;

// also starts an extra embedded web server on a random port
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
public class HelloControllerIntegrationTest {

    @Autowired
    private RestTestCleint client;

    @Test
    public void getHello() {
        client.get().uri("/").exchangeSuccessfully()
            .expectBody(String.class)
            .isEqualTo("Greetings from Spring Boot!");
    }
}