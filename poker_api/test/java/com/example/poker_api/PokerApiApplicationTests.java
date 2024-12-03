package com.example.poker_api;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import poker_api.src.main.java.com.example.poker_api.PokerApiApplication;

/**
 * Class to test the PokerApiApplication class
 * Ensures that contexts loads without issues
 * 
 * @author Tristan Curtis (tmc3221)
 */
@SpringBootTest(classes = PokerApiApplication.class)
class PokerApiApplicationTests {

	/** Our API application for testing */
    @Autowired
    private PokerApiApplication pokerApiApplication;

    /**
	 * Test to ensure that context loads without issue
	 */
    @Test
    void contextLoads() {
        assertNotNull(pokerApiApplication, "PokerApiApplication should be initialized.");
    }

    /**
	 * Check that the application starts correctly
	 */
    @Test
    void applicationStartsCorrectly() {
        // Verify that the application context loads and starts successfully
        assertNotNull(pokerApiApplication, "Application failed to start.");
    }
}
