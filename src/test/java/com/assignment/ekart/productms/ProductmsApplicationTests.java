package com.assignment.ekart.productms;

import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = ProductmsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductmsApplicationTests {

	@LocalServerPort
	private int port;
	@Autowired
	private ApplicationContext applicationContext;
	@Test
	void contextLoads() {
		assertNotNull(applicationContext);
	}

}
