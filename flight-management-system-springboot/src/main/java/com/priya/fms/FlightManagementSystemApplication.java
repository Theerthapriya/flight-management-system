package com.priya.fms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class FlightManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightManagementSystemApplication.class, args);
    }
}
