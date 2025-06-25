package com.example.test;

import de.adorsys.psd2.qwac.config.EnableTppRequestQwacFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main Spring Boot application (using MyCustomDataLoader for CRL fetching).
 */
@SpringBootApplication
@EnableTppRequestQwacFilter
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
