package com.skillstorm.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Form1099Test {

    @Test
    void testNoArgsConstructor() {
        // Test the no-args constructor
        Form1099 form = new Form1099();
        assertAll("Default constructor should not set any fields",
                () -> assertEquals(0, form.getId(), "Default id should be 0"),
                () -> assertNull(form.getTaxReturn(), "Default taxReturn should be null"),
                () -> assertNull(form.getPayer(), "Default payer should be null"),
                () -> assertEquals(0, form.getYear(), "Default year should be 0"),
                () -> assertEquals(0.0, form.getWages(), 0.001, "Default wages should be 0.0")
        );
    }

    @Test
    void testAllArgsConstructor() {
        // Test the constructor that takes wages
        Form1099 form = new Form1099(5000.0);
        assertEquals(5000.0, form.getWages(), 0.001, "Constructor should set wages correctly.");
    }

    @Test
    void testSettersAndGetters() {
        Form1099 form = new Form1099();
        form.setId(1);
        form.setPayer("Payer Inc.");
        form.setYear(2020);
        form.setWages(3000.0);

        TaxReturn taxReturn = new TaxReturn(); // Assuming you have a TaxReturn class
        form.setTaxReturn(taxReturn);

        assertAll("Testing setters and getters",
                () -> assertEquals(1, form.getId(), "Setter and getter for id should work correctly."),
                () -> assertEquals("Payer Inc.", form.getPayer(), "Setter and getter for payer should work correctly."),
                () -> assertEquals(2020, form.getYear(), "Setter and getter for year should work correctly."),
                () -> assertEquals(3000.0, form.getWages(), 0.001, "Setter and getter for wages should work correctly."),
                () -> assertEquals(taxReturn, form.getTaxReturn(), "Setter and getter for taxReturn should work correctly.")
        );
    }
}
