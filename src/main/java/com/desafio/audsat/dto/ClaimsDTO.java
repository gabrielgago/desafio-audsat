package com.desafio.audsat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record ClaimsDTO(@NotNull @Min(0) @Schema(description = "Car id", example = "1") Long carId,
                        @NotNull @Min(0) @Schema(description = "Driver id", example = "1") Long driverId,
                        @NotNull @Schema(description = "Event date", example = "99-99-9999 99:99:99")
                        @FutureOrPresent @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm:ss")
                        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss") LocalDateTime eventDate) {
}
