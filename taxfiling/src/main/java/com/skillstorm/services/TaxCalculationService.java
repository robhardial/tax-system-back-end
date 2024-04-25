package com.skillstorm.services;

import com.skillstorm.models.Form1099;
import com.skillstorm.models.FormW2;
import com.skillstorm.models.TaxReturn;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class TaxCalculationService {

    /**
     * Calculates the tax return amount for a given TaxReturn object.
     *
     * @param taxReturn the TaxReturn object containing the necessary information.
     * @return the calculated tax return amount.
     */
    public double calculateTaxReturn(TaxReturn taxReturn){

        if(taxReturn == null){
            throw new IllegalArgumentException("TaxReturn object cannot be null");
        }


        List<FormW2> w2Forms = taxReturn.getFormW2s();
        List<Form1099> form1099s = taxReturn.getForm1099s();

        double totalIncome = calculateTotalIncome(w2Forms, form1099s);
        double totalWithholdings = calculateTotalWithholdings(w2Forms);

        double socialSecurityTaxDue = calculateSocialSecurityTaxDue(totalIncome);
        double medicareTaxDue = calculateMedicareTaxDue(totalIncome);
        double federalTaxDue = calculateFederalTaxDue(totalIncome, taxReturn.getFilingStatus());

        double totalTaxDue = calculateTotalTaxDue(socialSecurityTaxDue, medicareTaxDue, federalTaxDue);

        double taxReturnAmount = calculateReturn(totalWithholdings, totalTaxDue);

        return taxReturnAmount;
    }


    /**
     * Calculates the total income of an individual by adding all wages from both W2 forms and form 1099s.
     *
     * @param w2Forms a list of W2 forms; each form contains the wages earned by the individual.
     * @param form1099s a list of Form 1099s; each form contains the other income (apart from the wages).
     *
     * @return the total income calculated as a sum of all wages from W2 forms and form 1099s.
     */
    public double calculateTotalIncome(List<FormW2> w2Forms, List<Form1099> form1099s) {
        double w2TotalIncome = 0.0;
        double form1099TotalIncome = 0.0;

        if(w2Forms != null && !w2Forms.isEmpty()){
            w2TotalIncome = w2Forms.stream()
                    .mapToDouble(FormW2::getWages)
                    .sum();
        }

        if (form1099s != null && !form1099s.isEmpty()) {
            form1099TotalIncome = form1099s.stream()
                    .mapToDouble(Form1099::getWages)
                    .sum();
        }

        return w2TotalIncome + form1099TotalIncome;
    }

    /**
     * Calculates the total federal income tax withheld from W2 forms.
     *
     * @param w2Forms a list of W2 forms; each form contains the tax withheld amount.
     * @return the total federal tax withheld calculated as a sum of all tax withheld from the W2 forms.
     */
    public double calculateTotalFederalTaxWithheld(List<FormW2> w2Forms) {
        if (w2Forms != null && !w2Forms.isEmpty()) {
            return w2Forms.stream()
                    .mapToDouble(FormW2::getFederalIncomeTaxWithheld)
                    .sum();
        } else {
            return 0.0;
        }
    }

    /**
     * Calculates the total Social Security tax withheld from W2 forms.
     *
     * @param w2Forms a list of W2 forms; each form contains the social security tax withheld amount.
     * @return the total social security tax withheld calculated as a sum of all tax withheld from the W2 forms.
     */
    public double calculateTotalSocialSecurityTaxWithheld(List<FormW2> w2Forms) {

        if (w2Forms == null || w2Forms.isEmpty()) {
            return 0.0;
        }

        return w2Forms.stream().mapToDouble(FormW2::getSocialSecurityTaxWithheld).sum();
    }

    /**
     * Calculates the total Medicare tax withheld from W2 forms.
     *
     * @param w2Forms a list of W2 forms; each form contains the Medicare tax withheld amount.
     * @return the total Medicare tax withheld calculated as a sum of all tax withheld from the W2 forms.
     */
    public double calculateTotalMedicareTaxWithheld(List<FormW2> w2Forms) {

        if (w2Forms == null || w2Forms.isEmpty()) {
            return 0.0;
        }

        return w2Forms.stream().mapToDouble(FormW2::getMedicareTaxWithheld).sum();
    }

    /**
     * Calculates the total withholding amount from W2 forms.
     *
     * @param w2Forms a list of W2 forms.
     * @return the total withholding amount calculated as a sum of the Federal, Social Security, and Medicare taxes.
     */
    public double calculateTotalWithholdings(List<FormW2> w2Forms) {


        double totalFederalTaxWithheld = calculateTotalFederalTaxWithheld(w2Forms);
        double totalSocialSecurityTaxWithheld = calculateTotalSocialSecurityTaxWithheld(w2Forms);
        double totalMedicareTaxWithheld = calculateTotalMedicareTaxWithheld(w2Forms);

        return totalFederalTaxWithheld + totalSocialSecurityTaxWithheld + totalMedicareTaxWithheld;
    }



    /**
     * Calculates the federal tax amount due based on the income and filing status.
     *
     * @param income the total income of the person.
     * @param filingStatus the filing status of the person, possible values are "Single" or "Jointly".
     * @return the amount of federal tax due.
     */
    public double calculateFederalTaxDue(double income, String filingStatus){
        double taxDue = 0.0;
        double standardDeduction = 0.0;

        if(filingStatus.equals("Single")){
            standardDeduction = 12200;
        } else if (filingStatus.equals("Jointly")) {
            standardDeduction = 24400;
        }

        double taxableIncome = Math.max(0, income - standardDeduction);

        if (filingStatus.equals("Single")) {
            if (taxableIncome <= 11600) {
                taxDue = taxableIncome * 0.1;
            } else if (taxableIncome <= 47150) {
                taxDue = 1160 + (taxableIncome - 11600) * 0.12;
            } else if (taxableIncome <= 100525) {
                taxDue = 5390 + (taxableIncome - 47150) * 0.22;
            } else if (taxableIncome <= 191950) {
                taxDue = 16603 + (taxableIncome - 100525) * 0.24;
            } else if (taxableIncome <= 243725) {
                taxDue = 38609 + (taxableIncome - 191950) * 0.32;
            } else {
                taxDue = 55049 + (taxableIncome - 243725) * 0.35;
            }
        } else if (filingStatus.equals("Jointly")) {
            if (taxableIncome <= 23200) {
                taxDue = taxableIncome * 0.1;
            } else if (taxableIncome <= 94300) {
                taxDue = 2320 + (taxableIncome - 23200) * 0.12;
            } else if (taxableIncome <= 201050) {
                taxDue = 10780 + (taxableIncome - 94300) * 0.22;
            } else if (taxableIncome <= 383900) {
                taxDue = 33220 + (taxableIncome - 201050) * 0.24;
            } else if (taxableIncome <= 487450) {
                taxDue = 77218 + (taxableIncome - 383900) * 0.32;
            } else {
                taxDue = 110058 + (taxableIncome - 487450) * 0.35;
            }
        }

        return taxDue;
    }

    /**
     * Calculates the Social Security tax due based on the income.
     *
     * @param income the total income of the person.
     * @return the amount of Social Security tax due.
     */
    public double calculateSocialSecurityTaxDue(double income) {
        final double SOCIAL_SECURITY_RATE = 0.062;
        final double SOCIAL_SECURITY_CAP = 160200;
        return Math.min(income, SOCIAL_SECURITY_CAP) * SOCIAL_SECURITY_RATE;
    }

    /**
     * Calculates the Medicare tax due based on the income.
     *
     * @param income the total income of the person.
     * @return the amount of Medicare tax due.
     */
    public double calculateMedicareTaxDue(double income) {
        final double MEDICARE_RATE = 0.0145;
        final double ADDITIONAL_MEDICARE_RATE = 0.009;
        final double ADDITIONAL_MEDICARE_THRESHOLD = 200000;
        double basicMedicareTax = income * MEDICARE_RATE;
        double additionalMedicareTax = 0.0;
        if (income > ADDITIONAL_MEDICARE_THRESHOLD) {
            additionalMedicareTax = (income - ADDITIONAL_MEDICARE_THRESHOLD) * ADDITIONAL_MEDICARE_RATE;
        }
        return basicMedicareTax + additionalMedicareTax;
    }

    /**
     * Calculates the total tax due by adding the amounts of social security tax, medicare tax, and federal tax due.
     *
     * @param socialSecurityTaxDue The amount of social security tax due.
     * @param medicareTaxDue The amount of medicare tax due.
     * @param federalTaxDue The amount of federal tax due.
     * @return The total tax due.
     */
    public double calculateTotalTaxDue(double socialSecurityTaxDue, double medicareTaxDue, double federalTaxDue) {
        return socialSecurityTaxDue + medicareTaxDue + federalTaxDue;
    }


    /**
     * Calculates the tax return value based on the amount of tax withheld and tax due.
     * @param taxWithheld The total amount of tax that has been withheld.
     * @param taxDue The total amount of tax that is due.
     * @return The calculated tax return value. Positive value indicates overpayment and negative value indicates underpayment.
     */
    public double calculateReturn(double taxWithheld, double taxDue){
        return taxWithheld - taxDue;
    }

}