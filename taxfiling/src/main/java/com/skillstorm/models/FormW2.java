package com.skillstorm.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "form_w2")
@Data
public class FormW2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "w2_id")
    private int id;

    //foreign key to person
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    //foreign key to employer
    @ManyToOne
    @JoinColumn(name = "employer_id")
    private Employer employer;

    //foreign key to tax-return
    @ManyToOne
    @JoinColumn(name = "tax_return_id")
    private TaxReturn taxReturn;

    private int year;
    private double wages;
    private double FederalIncomeTaxWithheld;
    private double SocialSecurityTaxWithheld;
    private double MedicareTaxWithheld;


}
