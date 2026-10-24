package com.training;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MaterialServiceApplication extends SpringBootServletInitializer {

	private static final Logger logger = LoggerFactory.getLogger(MaterialServiceApplication.class);
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(MaterialServiceApplication.class);
	}		
	
	public static void main(String[] args) {
		SpringApplication.run(MaterialServiceApplication.class, args);
	}

}
