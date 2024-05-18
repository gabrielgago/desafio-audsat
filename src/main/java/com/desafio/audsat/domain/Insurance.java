package com.desafio.audsat.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "insurences")
@Getter
@Setter
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(name = "customer", description = "Customer id", example = "1")
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    private LocalDateTime creationDate;
    private LocalDateTime updatedDate;

    @Schema(name = "car", description = "Car id", example = "1")
    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @Schema(name = "active", description = "If insurance is active", example = "true")
    private boolean active;
}
