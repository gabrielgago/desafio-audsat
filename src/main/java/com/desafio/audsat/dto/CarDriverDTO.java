package com.desafio.audsat.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CarDriverDTO(@NotNull @Min(0) Long driverId, @NotNull @Min(0) Long carId, boolean isMainDriver) {
}
