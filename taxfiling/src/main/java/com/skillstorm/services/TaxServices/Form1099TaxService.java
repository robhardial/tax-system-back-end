package com.skillstorm.services.TaxServices;

import com.skillstorm.models.Form1099;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Form1099TaxService {

    private static final double SOCIAL_SECURITY_RATE = 0.062;
    private static final double MEDICARE_RATE = 0.0145;
    private static final double SOCIAL_SECURITY_CAP = 168600;

    public double sum1099Wages(List<Form1099> form1099s) {
        return form1099s.stream().mapToDouble(Form1099::getWages).sum();
    }

    public double calculateSelfEmploymentTax(double total1099Income) {
        double socialSecurityTax = calculateSelfEmploymentSocialSecurityTax(total1099Income);
        double medicareTax = calculateSelfEmploymentMedicareTax(total1099Income);
        return socialSecurityTax + medicareTax;
    }

    private double calculateSelfEmploymentSocialSecurityTax(double total1099Income) {
        double taxableIncome = Math.min(total1099Income, SOCIAL_SECURITY_CAP);
        return taxableIncome * SOCIAL_SECURITY_RATE;
    }

    private double calculateSelfEmploymentMedicareTax(double total1099Income) {
        return total1099Income * MEDICARE_RATE;
    }
}
