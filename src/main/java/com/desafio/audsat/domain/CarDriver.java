package com.desafio.audsat.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "car_drivers")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDriver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @ManyToOne(optional = false)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @Column(name = "is_main_driver", nullable = false)
    private Boolean isMainDriver;

}
