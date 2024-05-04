package com.skillstorm.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "tax_return")
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class TaxReturn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tax_return_id")
    private int id;

    // foreign key to Person
    @ManyToOne()
    @JoinColumn(name = "person_id")
    @JsonIgnore
    private Person person;

    private int year;

    @Column(name = "filing_status")
    private String filingStatus;

    //added to keep track of tax filing
    @Column
    private boolean completed;

    // Added column to store the total refund or due amount after its calculated
    @Column(name = "total_refund_due")
    private Double totalRefundDue;

    //    private double totalIncome;
    //    private double totalWithholding;
    //    private double totalDeductions;
    //

    @JsonIgnore
    @OneToMany(mappedBy = "taxReturn", cascade = CascadeType.ALL)
    private List<FormW2> formW2s;

    @JsonIgnore
    @OneToMany(mappedBy = "taxReturn", cascade = CascadeType.ALL)
    private List<Form1099> form1099s;

}