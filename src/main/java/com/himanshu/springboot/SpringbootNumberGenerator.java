package com.himanshu.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Himanshu Varshney on 12/16/2020
 */

// @SpringBootApplication annotation enables the auto-configuration feature of the spring boot module
//(i.e. java-based configuration and component scanning).
@SpringBootApplication
public class SpringbootNumberGenerator {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringbootNumberGenerator.class);
	
	public static void main(String[] args) {
		// The "run()" method returns the "ConfigurableApplicationContext" instance which can be further
		//used by the spring application.
		SpringApplication.run(SpringbootNumberGenerator.class, args);
		LOGGER.info("Springboot application for decreasing order number generator till 0 started successfully.");
	}
}
