package services;

import com.skillstorm.services.TaxCalculationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaxCalculationServiceTest {

    private TaxCalculationService service;

    @BeforeEach
    public void setUp() {
        service = new TaxCalculationService();
    }

    @Test
    public void testCalculateTaxDue_Single_LowIncome() {
        // Testing the lowest bracket for Single filers
        double income = 5000;
        String filingStatus = "Single";
        double expectedTaxDue = income * 0.1;
        assertEquals(expectedTaxDue, service.calculateTaxDue(income, filingStatus), "Testing the lowest bracket for Single filers failed");
    }

    @Test
    public void testCalculateTaxDue_Single_MiddleIncome() {
        // Testing a middle bracket for Single filers
        double income = 50000;
        String filingStatus = "Single";
        double expectedTaxDue = 4617.5 + (income - 40125) * 0.22;
        assertEquals(expectedTaxDue, service.calculateTaxDue(income, filingStatus), "Testing a middle bracket for Single filers failed");
    }

    @Test
    public void testCalculateTaxDue_Married_LowIncome() {
        // Testing the lowest bracket for Married filers
        double income = 10000;
        String filingStatus = "Married";
        double expectedTaxDue = income * 0.1;
        assertEquals(expectedTaxDue, service.calculateTaxDue(income, filingStatus), "Testing the lowest bracket for Married filers failed");
    }

    @Test
    public void testCalculateTaxDue_Married_HighIncome() {
        // Testing a higher bracket for Married filers
        double income = 500000;
        String filingStatus = "Married";
        double expectedTaxDue = 94735 + (income - 414700) * 0.35;
        assertEquals(expectedTaxDue, service.calculateTaxDue(income, filingStatus), "Testing a higher bracket for Married filers failed");
    }
}