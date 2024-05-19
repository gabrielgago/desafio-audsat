package com.desafio.audsat.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "drivers")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "document", unique = true, nullable = false)
    private String document;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Builder.Default
    @OneToMany(mappedBy = "driver")
    private Set<CarDriver> carDrivers = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "driver")
    private Set<Customer> customers = new HashSet<>();

}
