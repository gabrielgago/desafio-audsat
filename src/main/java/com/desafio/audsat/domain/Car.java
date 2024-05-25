package com.desafio.audsat.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Year;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cars")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;
    private String manufacturer;
    @JsonAlias("year")
    private Year yearOfManufacturing;

    @Column(name = "fipe_value")
    private BigDecimal fipeValue;

    @Builder.Default
    @OneToMany(mappedBy = "car")
    private Set<Insurance> insurances = new HashSet<>();

    @OneToMany(mappedBy = "car")
    private Set<CarDriver> carDrivers;

}
