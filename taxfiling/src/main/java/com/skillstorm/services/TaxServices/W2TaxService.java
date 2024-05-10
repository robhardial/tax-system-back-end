package com.skillstorm.services.TaxServices;

import com.skillstorm.models.FormW2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class W2TaxService {

    /**
     * Sums the wages from a list of FormW2 objects.
     *
     * @param w2Forms the list of FormW2 objects
     * @return the sum of wages as a double
     */
    public double sumW2Wages(List<FormW2> w2Forms) {
        return w2Forms.stream().mapToDouble(FormW2::getWages).sum();
    }

    /**
     * Calculates the total federal income tax withheld from a list of FormW2 objects.
     *
     * @param w2Forms the list of FormW2 objects
     * @return the total federal income tax withheld as a double
     */
    public double totalFederalWithheld(List<FormW2> w2Forms) {
        return w2Forms.stream().mapToDouble(FormW2::getFederalIncomeTaxWithheld).sum();
    }

    /**
     * Calculates the total social security tax withheld from a list of FormW2 objects.
     *
     * @param w2Forms the list of FormW2 objects
     * @return the total social security tax withheld as a double
     */
    public double totalSocialSecurityWithheld(List<FormW2> w2Forms) {
        return w2Forms.stream().mapToDouble(FormW2::getSocialSecurityTaxWithheld).sum();
    }

    /**
     * Calculates the total Medicare tax withheld from a list of FormW2 objects.
     *
     * @param w2Forms the list of FormW2 objects
     * @return the total Medicare tax withheld as a double
     */
    public double totalMedicareWithheld(List<FormW2> w2Forms) {
        return w2Forms.stream().mapToDouble(FormW2::getMedicareTaxWithheld).sum();
    }
}
