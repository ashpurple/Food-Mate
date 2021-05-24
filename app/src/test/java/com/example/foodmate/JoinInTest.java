package com.example.foodmate;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class JoinInTest {
    private ListDetailActivity join;

    @Before
    public void setUp() {
        join = new ListDetailActivity();
    }

    @Test
    public void joinInTest() {
        assertNotNull(join);
    }
}
