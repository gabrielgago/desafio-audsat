package com.desafio.audsat.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "insurences")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Insurance extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(name = "customer", description = "Customer id", example = "1")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @JsonProperty("creationDate")
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @Schema(name = "car", description = "Car id", example = "1")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @Schema(name = "active", description = "If insurance is active", example = "true")
    private boolean active;

}
