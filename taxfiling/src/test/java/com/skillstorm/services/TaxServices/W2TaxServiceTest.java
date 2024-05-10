package com.skillstorm.services.TaxServices;

import com.skillstorm.models.FormW2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class W2TaxServiceTest {

    private W2TaxService taxService;

    @BeforeEach
    void setUp() {
        taxService = new W2TaxService();
    }

    @Test
    void testSumW2WagesWithMultipleEntries() {
        List<FormW2> w2Forms = Arrays.asList(new FormW2(20000, 3000, 1200, 800),
                new FormW2(15000, 2200, 1100, 700),
                new FormW2(30000, 4500, 1800, 1200));
        assertEquals(65000, taxService.sumW2Wages(w2Forms), 0.001);
    }

    @Test
    void testSumW2WagesWithEmptyList() {
        assertEquals(0, taxService.sumW2Wages(Collections.emptyList()), 0.001);
    }

    @Test
    void testTotalFederalWithheld() {
        List<FormW2> w2Forms = Arrays.asList(new FormW2(20000, 3000, 1200, 800),
                new FormW2(15000, 2200, 1100, 700));
        assertEquals(5200, taxService.totalFederalWithheld(w2Forms), 0.001);
    }

    @Test
    void testTotalSocialSecurityWithheld() {
        List<FormW2> w2Forms = Arrays.asList(new FormW2(20000, 3000, 1200, 800),
                new FormW2(15000, 2200, 1100, 700));
        assertEquals(2300, taxService.totalSocialSecurityWithheld(w2Forms), 0.001);
    }

    @Test
    void testTotalMedicareWithheld() {
        List<FormW2> w2Forms = Arrays.asList(new FormW2(20000, 3000, 1200, 800),
                new FormW2(15000, 2200, 1100, 700));
        assertEquals(1500, taxService.totalMedicareWithheld(w2Forms), 0.001);
    }

    @Test
    void testNullInput() {
        assertAll("Testing null input handling",
                () -> assertThrows(NullPointerException.class, () -> taxService.sumW2Wages(null)),
                () -> assertThrows(NullPointerException.class, () -> taxService.totalFederalWithheld(null)),
                () -> assertThrows(NullPointerException.class, () -> taxService.totalSocialSecurityWithheld(null)),
                () -> assertThrows(NullPointerException.class, () -> taxService.totalMedicareWithheld(null))
        );
    }
}