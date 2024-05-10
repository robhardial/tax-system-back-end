package com.skillstorm.services.TaxServices;

import com.skillstorm.models.TaxReturn;
import com.skillstorm.services.TaxReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaxCalculationService {

    @Autowired
    private W2TaxService w2TaxService;
    @Autowired
    private Form1099TaxService form1099TaxService;
    @Autowired
    private TaxReturnService taxReturnService;

    /**
     * A service class for calculating tax based on different tax forms.
     */
    public TaxCalculationService(W2TaxService w2TaxService, Form1099TaxService form1099TaxService) {
        this.w2TaxService = w2TaxService;
        this.form1099TaxService = form1099TaxService;
    }


    /**
     * Calculates the total tax due for a given TaxReturn object.
     *
     * @param taxReturn the TaxReturn object for which to calculate the total tax due
     * @return the total tax due as a double
     */
    public double calculateTotalTaxDue(TaxReturn taxReturn) {
        double totalW2Wages = w2TaxService.sumW2Wages(taxReturn.getFormW2s());
        double total1099Wages = form1099TaxService.sum1099Wages(taxReturn.getForm1099s());

        double federalWithheld = w2TaxService.totalFederalWithheld(taxReturn.getFormW2s());
        double socialSecurityWithheld = w2TaxService.totalSocialSecurityWithheld(taxReturn.getFormW2s());
        double medicareWithheld = w2TaxService.totalMedicareWithheld(taxReturn.getFormW2s());

        double selfEmploymentTax = form1099TaxService.calculateSelfEmploymentTax(total1099Wages);

        double totalWages = totalW2Wages + total1099Wages;
        double grossTaxDue = applyTaxRatesAndDeductions(totalWages, taxReturn.getFilingStatus());

        double totalTaxWithheld = federalWithheld + socialSecurityWithheld + medicareWithheld;
        double netTaxDue = grossTaxDue + selfEmploymentTax - totalTaxWithheld;

        taxReturn.setTotalRefundDue(netTaxDue);
        taxReturn.setCompleted(true);

        taxReturnService.updateTaxReturn(taxReturn);

        return netTaxDue;
    }

    /**
     * Applies tax rates and deductions to calculate the total tax due based on the total wages and filing status.
     *
     * @param totalWages    the total wages as a double
     * @param filingStatus  the filing status as a String ("Single" or "Jointly")
     * @return the total tax due as a double
     */
    private double applyTaxRatesAndDeductions(double totalWages, String filingStatus) {
        double[] incomeThresholds;
        double[] taxRates;
        double standardDeduction;

        if (filingStatus.equals("Single")) {
            standardDeduction = 13850;
            incomeThresholds = new double[]{11600, 47150, 100525, 191950, 243725, 609350};
            taxRates = new double[]{0.10, 0.12, 0.22, 0.24, 0.32, 0.35, 0.37};
        } else if (filingStatus.equals("Jointly")) {
            standardDeduction = 29095;
            incomeThresholds = new double[]{23200, 94300, 201050, 383900, 487450, 731200};
            taxRates = new double[]{0.10, 0.12, 0.22, 0.24, 0.32, 0.35, 0.37};
        } else {
            standardDeduction = 0;  // Default, adjust as necessary
            incomeThresholds = new double[0];
            taxRates = new double[0];
        }

        double taxableIncome = Math.max(0, totalWages - standardDeduction);
        return calculateMarginalTax(taxableIncome, incomeThresholds, taxRates);
    }

    /**
     * Calculates the marginal tax based on the taxable income, income thresholds, and tax rates.
     *
     * @param taxableIncome the taxable income as a double
     * @param thresholds    the income thresholds as an array of double
     * @param rates         the tax rates corresponding to the income thresholds as an array of double
     * @return the calculated marginal tax as a double
     */
    private double calculateMarginalTax(double taxableIncome, double[] thresholds, double[] rates) {
        double tax = 0;
        double previousThreshold = 0;

        for (int i = 0; i < thresholds.length; i++) {
            if (taxableIncome > thresholds[i]) {
                tax += (thresholds[i] - previousThreshold) * rates[i];
                previousThreshold = thresholds[i];
            } else {
                tax += (taxableIncome - previousThreshold) * rates[i];
                break;
            }
        }

        if (taxableIncome > thresholds[thresholds.length - 1]) {
            tax += (taxableIncome - thresholds[thresholds.length - 1]) * rates[rates.length - 1];
        }

        return tax;
    }
}
