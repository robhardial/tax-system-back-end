package com.skillstorm.services;

import com.skillstorm.models.Form1099;
import com.skillstorm.models.FormW2;
import com.skillstorm.models.TaxReturn;
import org.springframework.beans.factory.annotation.Autowired;
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

        List<FormW2> w2Forms = taxReturn.getFormW2s();
        List<Form1099> form1099s = taxReturn.getForm1099s();

        double totalIncome = calculateTotalIncome(w2Forms,form1099s);

        double totalWithholdings = calculateTotalWithholdings(w2Forms);

        double  taxDue = calculateTaxDue(totalIncome, taxReturn.getFilingStatus());

        double taxReturnAmount = calculateReturn(totalWithholdings, taxDue);

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
     * <p>This function calculates the amount of tax due based on the income and filing status of the person.
     *    There are different tax rates and tax brackets for each filing status.</p>
     *
     * @param  income        the total income of the person
     * @param  filingStatus  the filing status of the person, "Single" or "Married"
     * @return               the amount of tax due.
     */
    public double calculateTaxDue(double income, String filingStatus){
        double taxDue = 0.0;
        if (filingStatus.equals("Single")) {
            if (income <= 11600) {
                taxDue = income * 0.1;
            } else if (income <= 47150) {
                taxDue = 1160 + (income - 11600) * 0.12;
            } else if (income <= 100525) {
                taxDue = 5390 + (income - 47150) * 0.22;
            } else if (income <= 191950) {
                taxDue = 16603 + (income - 100525) * 0.24;
            } else if (income <= 243725) {
                taxDue = 38609 + (income - 191950) * 0.32;
            } else {
                taxDue = 55049 + (income - 243725) * 0.35;
            }
        } else if (filingStatus.equals("Jointly")) {
            if (income <= 23200) {
                taxDue = income * 0.1;
            } else if (income <= 94300) {
                taxDue = 2320 + (income - 23200) * 0.12;
            } else if (income <= 201050) {
                taxDue = 10780 + (income - 94300) * 0.22;
            } else if (income <= 383900) {
                taxDue = 33220 + (income - 201050) * 0.24;
            } else if (income <= 487450) {
                taxDue = 77218 + (income - 383900) * 0.32;
            } else {
                taxDue = 110058 + (income - 487450) * 0.35;
            }
        }

        return taxDue;
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