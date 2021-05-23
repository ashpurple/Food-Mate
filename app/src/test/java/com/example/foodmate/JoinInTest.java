package com.example.foodmate;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;

public class JoinInTest {
    private ListDetailActivity joinIn;

    @Before
    public void setUp() {
        joinIn = new ListDetailActivity();
    }

    @Test
    public void joinInTest() {
        assertNotNull(joinIn);
    }
}
