package com.skillstorm.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "tax_retun")
@Data
public class TaxReturn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tax_return_id")
    private int id;

    //foreign key to Person
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    private int year;
    private String filingStatus;

    private double totalIncome;
    private double totalFederalTaxWitheld;
    private double totalDeductions;
    private double totalCredits;


    @OneToMany(mappedBy = "taxReturn", cascade = CascadeType.ALL)
    private List<FormW2> formW2s;

    @OneToMany(mappedBy = "taxReturn", cascade = CascadeType.ALL)
    private List<Form1099> form1099s;

}
