package com.example.foodmate;

import junit.framework.TestCase;

import org.junit.Test;

public class showDataTest extends TestCase{
    ListActivity test;

    @Test
    public void setUp() {
        test = new ListActivity();
    }

    @Test
    public void testShowData(){
        assertNotNull(test);   }
}