package com.desafio.audsat.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cars")
@Getter
@Setter
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;
    private String manufacturer;
    private String year;

    @Column(name = "fipe_value")
    private BigDecimal fipeValue;

    @OneToMany(mappedBy = "car")
    private Set<Insurance> insurances = new HashSet<>();

    @OneToMany(mappedBy = "car")
    private Set<CarDriver> carDrivers;
}
