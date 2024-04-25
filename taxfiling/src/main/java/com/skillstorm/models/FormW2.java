package com.skillstorm.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CollectionId;

@Entity
@Table(name = "form_w2")
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class FormW2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "w2_id")
    private int id;

    //foreign key to employer
    @ManyToOne
    @JoinColumn(name = "employer_id")
    private Employer employer;

    //foreign key to tax-return
    @ManyToOne
    @JoinColumn(name = "tax_return_id")
    private TaxReturn taxReturn;

    @Column
    private int year;

    @Column
    private double wages;

    @Column(name = "federal_income_tax_withheld")
    private double FederalIncomeTaxWithheld;
    @Column(name = "social_security_tax_withheld")
    private double SocialSecurityTaxWithheld;
    @Column(name = "medicare_tax_withheld")
    private double MedicareTaxWithheld;

}
