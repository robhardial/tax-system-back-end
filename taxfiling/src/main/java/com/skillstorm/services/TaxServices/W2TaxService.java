package com.skillstorm.services.TaxServices;

import com.skillstorm.models.FormW2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class W2TaxService {

    public double sumW2Wages(List<FormW2> w2Forms) {
        return w2Forms.stream().mapToDouble(FormW2::getWages).sum();
    }

    public double totalFederalWithheld(List<FormW2> w2Forms) {
        return w2Forms.stream().mapToDouble(FormW2::getFederalIncomeTaxWithheld).sum();
    }

    public double totalSocialSecurityWithheld(List<FormW2> w2Forms) {
        return w2Forms.stream().mapToDouble(FormW2::getSocialSecurityTaxWithheld).sum();
    }

    public double totalMedicareWithheld(List<FormW2> w2Forms) {
        return w2Forms.stream().mapToDouble(FormW2::getMedicareTaxWithheld).sum();
    }
}
