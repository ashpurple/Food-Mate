package com.example.foodmate;

import org.junit.Before;
import org.junit.Test;
import junit.framework.TestCase;

public class SignUpTest extends TestCase{
    private SignUpActivity signUp;

    @Before
    public void setUp() {
        signUp = new SignUpActivity();
    }

    @Test
    public void signUpTest() {
        assertNotNull(signUp);
    }
}
