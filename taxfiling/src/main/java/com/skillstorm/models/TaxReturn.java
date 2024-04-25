package com.skillstorm.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    private int year;

    @Column(name = "filing_status")
    private String filingStatus;

//    private double totalIncome;
//    private double totalWithholding;
//    private double totalDeductions;
//

    @OneToMany(mappedBy = "taxReturn", cascade = CascadeType.ALL)
    private List<FormW2> formW2s;

    @OneToMany(mappedBy = "taxReturn", cascade = CascadeType.ALL)
    private List<Form1099> form1099s;

}
