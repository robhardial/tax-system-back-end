package com.skillstorm.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "form_1099")
@Data
public class Form1099 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "form_1099_id")
    private int id;

    //foreign key to person
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    //foreign key to tax-return
    @ManyToOne
    @JoinColumn(name = "tax_return_id")
    private TaxReturn taxReturn;

    @Column
    private int year;

    @Column
    private double wages;

}
