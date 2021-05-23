package com.example.foodmate;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class PostUpdateTest {
    private WritingActivity postUpdate;

    @Before
    public void setUp(){
        postUpdate = new WritingActivity();
    }

    @Test
    public void postUpdateTest() {
        assertNotNull(postUpdate);
    }
}
