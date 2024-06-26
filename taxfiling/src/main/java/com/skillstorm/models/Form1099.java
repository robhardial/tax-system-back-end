package com.skillstorm.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "form_1099")
@Data
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Form1099 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "form_1099_id")
    private int id;

    //foreign key to tax-return
    @ManyToOne
    @JoinColumn(name = "tax_return_id")
    @JsonIgnore
    private TaxReturn taxReturn;

    @Column
    private String payer;

    @Column
    private int year;

    @Column
    private double wages;

    public Form1099(double wages) {
        this.wages = wages;
    }
}
