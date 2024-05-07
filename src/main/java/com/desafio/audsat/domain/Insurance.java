package com.desafio.audsat.domain;

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

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    private LocalDateTime creationDate;
    private LocalDateTime updatedDate;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    private boolean active;
}
