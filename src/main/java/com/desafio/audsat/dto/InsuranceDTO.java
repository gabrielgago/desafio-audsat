package com.desafio.audsat.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record InsuranceDTO(@NotNull @Min(0) Long carId, @NotNull @Min(0) Long customerId, boolean isActive) {
}