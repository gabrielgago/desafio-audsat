package com.desafio.audsat.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "drivers")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "document")
    private String document;

    @Column(name = "birthdate")
    private String birthdate;

    @OneToMany(mappedBy = "driver")
    private Set<CarDriver> carDrivers = new HashSet<>();

    @OneToMany(mappedBy = "driver")
    private Set<Customer> customers = new HashSet<>();
}
