package com.example.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main Spring Boot application (using MyCustomDataLoader for CRL fetching).
 */
@SpringBootApplication
public class TestApplication {

    private static final Logger log = LoggerFactory.getLogger(TestApplication.class);

    public static void main(String[] args) {
        log.info("Starting TestApplication (using MyCustomDataLoader for CRL fetching)");
        SpringApplication.run(TestApplication.class, args);
    }

}
