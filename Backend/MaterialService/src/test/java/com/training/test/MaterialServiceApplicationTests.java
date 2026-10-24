package com.training.test;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

//import com.accenture.lkm.test.controller.MaterialControllerTest;

@SpringBootTest
public class MaterialServiceApplicationTests {
	private static final Logger logger = LoggerFactory.getLogger(MaterialServiceApplicationTests.class);

	@Test
	public void contextLoads() {
		logger.info("UNIT TEST STARTED");
		assertEquals("Spring Boot", "Spring Boot","STRING VALUE NOT SAME!");
	}

}
