package com.desafio.audsat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record CustomerDTO(@NotNull @Schema(description = "Name of the insured", example = "Gabriel") String name,
                          @NotNull @Email @Schema(description = "Email of the insured", example = "gfrael@gmail.com") String email,
                          @NotNull @CPF @Schema(description = "CPF of the insured", example = "326.639.710-06") String document,
                          Long driverId) {

}
