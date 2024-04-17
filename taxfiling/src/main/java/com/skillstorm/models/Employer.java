package com.skillstorm.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "employer")
@Data
public class Employer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employer_id")
    private int id;
    private String ein;
    private String name;
    private String address;

    @OneToMany(mappedBy = "employer")
    private List<FormW2> formW2s;

}
