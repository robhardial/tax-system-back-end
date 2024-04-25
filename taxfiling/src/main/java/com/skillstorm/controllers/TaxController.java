package com.skillstorm.controllers;

import com.skillstorm.models.TaxReturn;
import com.skillstorm.services.TaxCalculationService;
import com.skillstorm.services.TaxReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tax")
public class TaxController {

    @Autowired
    private TaxCalculationService taxCalculationService;

    @Autowired
    private TaxReturnService taxReturnService;

    /**
     * Calculates the tax return amount for a given TaxReturn object.
     *
     * @param id the ID of the tax return to calculate.
     * @return the calculated tax return amount.
     */
    @PostMapping("/calculate/{id}")
    public double calculateTaxReturn(@PathVariable("id") int id){
        TaxReturn taxReturn = taxReturnService.getTaxReturnById(id);

        return taxCalculationService.calculateTaxReturn(taxReturn);

    }

}