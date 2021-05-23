package com.example.foodmate;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class LogInTest {
        private LoginActivity login;

        @Before
        public void setUp() {
            login = new LoginActivity();
        }

        @Test
        public void loginTest() {
            assertNotNull(login);
        }

}
