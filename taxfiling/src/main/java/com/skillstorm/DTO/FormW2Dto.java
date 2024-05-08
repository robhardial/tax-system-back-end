package com.skillstorm.DTO;

import lombok.Data;

@Data
public class FormW2Dto {
    private int employerId;
    private int taxReturnId;
    private int year;
    private double wages;
    private double federalIncomeTaxWithheld;
    private double socialSecurityTaxWithheld;
    private double medicareTaxWithheld;
}
