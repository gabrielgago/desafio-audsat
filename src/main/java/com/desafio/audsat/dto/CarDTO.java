package com.desafio.audsat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

public record CarDTO(@NotBlank String model,
                     @NotBlank String manufacturer,
                     @DateTimeFormat(pattern = "yyyy") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy") Year year,
                     @NotNull BigDecimal fipeValue,
                     List<Long> carsDrivers,
                     List<Long> insurancesId) {
}
