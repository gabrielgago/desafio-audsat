package com.desafio.audsat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record InsuranceDTO(@NotNull @Min(0) @JsonProperty("car_id") Long carId,
                           @JsonProperty("customer_id") @NotNull @Min(0) Long customerId,
                           @JsonProperty(value = "is_active", defaultValue = "true") boolean isActive) {
}
