package com.skillstorm.services.TaxServices;

import com.skillstorm.models.Form1099;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Form1099TaxService {

    private static final double SOCIAL_SECURITY_RATE = 0.062;
    private static final double MEDICARE_RATE = 0.0145;
    private static final double SOCIAL_SECURITY_CAP = 168600;

    /**
     * Calculates the sum of wages for a list of Form1099 objects.
     *
     * @param form1099s the list of Form1099 objects
     * @return the sum of wages as a double
     */
    public double sum1099Wages(List<Form1099> form1099s) {
        return form1099s.stream().mapToDouble(Form1099::getWages).sum();
    }

    /**
     * Calculates the self-employment tax based on the total 1099 income.
     *
     * @param total1099Income the total 1099 income as a double
     * @return the self-employment tax as a double
     */
    public double calculateSelfEmploymentTax(double total1099Income) {
        double socialSecurityTax = calculateSelfEmploymentSocialSecurityTax(total1099Income);
        double medicareTax = calculateSelfEmploymentMedicareTax(total1099Income);
        return socialSecurityTax + medicareTax;
    }

    /**
     * Calculates the self-employment social security tax based on the total 1099 income.
     *
     * @param total1099Income the total 1099 income as a double
     * @return the self-employment social security tax as a double
     */
    private double calculateSelfEmploymentSocialSecurityTax(double total1099Income) {
        double taxableIncome = Math.min(total1099Income, SOCIAL_SECURITY_CAP);
        return taxableIncome * SOCIAL_SECURITY_RATE;
    }

    /**
     * Calculates the self-employment Medicare tax based on the total 1099 income.
     *
     * @param total1099Income the total 1099 income as a double
     * @return the self-employment Medicare tax as a double
     */
    private double calculateSelfEmploymentMedicareTax(double total1099Income) {
        return total1099Income * MEDICARE_RATE;
    }
}
