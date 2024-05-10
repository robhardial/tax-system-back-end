package com.skillstorm.services.TaxServices;

import com.skillstorm.models.Form1099;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Form1099TaxServiceTest {

    private Form1099TaxService taxService;

    @BeforeEach
    void setUp() {
        taxService = new Form1099TaxService();
    }

    @Test
    void testSum1099WagesWithMultipleEntries() {
        List<Form1099> form1099s = Arrays.asList(new Form1099(20000), new Form1099(15000), new Form1099(30000));
        assertEquals(65000, taxService.sum1099Wages(form1099s), 0.001);
    }

    @Test
    void testSum1099WagesWithEmptyList() {
        assertEquals(0, taxService.sum1099Wages(Collections.emptyList()), 0.001);
    }

    @Test
    void testSum1099WagesWithNull() {
        assertThrows(NullPointerException.class, () -> taxService.sum1099Wages(null));
    }

    @Test
    void testCalculateSelfEmploymentTax() {
        double incomeBelowCap = 100000;
        assertEquals(8620, taxService.calculateSelfEmploymentTax(incomeBelowCap), 0.001);
        double incomeAboveCap = 200000;
        assertEquals(14509, taxService.calculateSelfEmploymentTax(incomeAboveCap), 0.001);
    }
}