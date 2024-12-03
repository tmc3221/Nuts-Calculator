package com.example.poker_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for the Spring Boot application
 * Starts the Poker API application
 * 
 * @author Tristan Curtis (tmc3221)
 */
@SpringBootApplication
public class PokerApiApplication {

    /**
     * Main method to start the Spring Boot application
     * @param args the arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(PokerApiApplication.class, args);
    }
}
