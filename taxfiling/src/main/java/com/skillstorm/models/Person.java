package com.skillstorm.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.text.Normalizer;
import java.util.List;

@Entity
@Table(name = "persons")
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private int id;

    @NotNull
    @Column
    private int ssn;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @Column
    private String address;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<TaxReturn> taxReturns;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<FormW2> formW2s;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Form1099> form1099s;

    @OneToOne
    @JoinColumn(name = "user_id") // Define the foreign key column
    private User user;

}
