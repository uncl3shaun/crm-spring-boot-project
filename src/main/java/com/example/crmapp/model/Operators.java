package com.example.crmapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "operators")
@Getter
@Setter
public class Operators {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;


    @Column(name = "department")
    private String department;


    @ManyToMany(mappedBy = "operators", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ApplicationRequest> requests;
}