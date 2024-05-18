package com.desafio.audsat.dto;

import com.desafio.audsat.domain.Insurance;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record InsuranceDTO(@NotNull @Min(0) Long carId, @NotNull @Min(0) Long costumerId, boolean isActive) {

    public static InsuranceDTO from(Insurance insurance) {
        return new InsuranceDTO(insurance.getCar().getId(), insurance.getCustomer().getId(), insurance.isActive());
    }

}
