/*
Tests the HelloController GET Endpoint
uses a fake HTTP client (MockMvc)
does NOT open a real network port
*/

package com.example.springboot;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

// loads full Spring application context for test
@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerTest {

	// @Autowired tells Spring to automatically fill in this field with a managed bean of the right type (dependency injection)
	@Autowired
	private MockMvc mvc;

	// annotation: when you run ./gradlew test, this gets called
	@Test
	public void getHello() throws Exception {
		mvc.perform(get("/").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk()).andExpect(content()
			.string(equalTo("Greetings from Spring Boot!")));
	}
}