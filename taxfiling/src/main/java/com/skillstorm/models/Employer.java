package com.skillstorm.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "employer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employer_id")
    private int id;

    @NotNull
    private String ein;

    @NotNull
    private String name;

    @NotNull
    private String address;

    @JsonIgnore
    @OneToMany(mappedBy = "employer")
    private List<FormW2> formW2s;

}
