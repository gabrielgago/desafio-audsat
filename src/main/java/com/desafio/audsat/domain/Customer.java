package com.desafio.audsat.domain;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "id", description = "Customer id", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(name = "name", description = "Customer name", example = "Gabriel", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Column(unique = true, nullable = false)
    @Schema(name = "email", description = "Customer mail", example = "gfrael@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Column(unique = true, nullable = false)
    @Schema(name = "document", description = "Customer Document (CPF)", example = "xxx.xxx.xxx-27", requiredMode = Schema.RequiredMode.REQUIRED)
    private String document;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    @Schema(name = "driver", description = "Driver id", example = "1")
    private Driver driver;

    @OneToMany(mappedBy = "customer", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @Schema(name = "insurances", description = "Insurances ids", example = "[1,2,3]")
    private Set<Insurance> insurances = new HashSet<>();
}
