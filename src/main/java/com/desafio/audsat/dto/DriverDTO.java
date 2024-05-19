package com.desafio.audsat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public record DriverDTO(@NotBlank String document,
                        @NotNull @DateTimeFormat(pattern = "dd-MM-yyyy") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY") LocalDate birthdate,
                        List<Long> carsDrivers,
                        List<Long> costumers) {
}
