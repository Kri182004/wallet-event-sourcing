package com.wallet;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WalletApplication {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(WalletApplication.class, args);
    }
}
//This code defines the main entry point for a Spring Boot application called WalletApplication.
// The @SpringBootApplication annotation indicates that this is a Spring Boot application, and it enables auto-configuration and component scanning.
// The main method uses SpringApplication.run() to launch the application, which will start an embedded web server and initialize the Spring context.
