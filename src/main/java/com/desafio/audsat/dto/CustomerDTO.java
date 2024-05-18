package com.desafio.audsat.dto;

import com.desafio.audsat.domain.Customer;
import com.desafio.audsat.domain.Driver;
import com.desafio.audsat.domain.Insurance;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Set;

public record CustomerDTO(@NotNull @Schema(description = "Name of the insured", example = "Gabriel") String name,
                          @NotNull @Email @Schema(description = "Email of the insured", example = "gfrael@gmail.com") String email,
                          @NotNull @CPF @Schema(description = "CPF of the insured", example = "326.639.710-06") String document,
                          Driver driver, Set<Insurance> insurances) {

    public static CustomerDTO of(Customer customer) {
        return new CustomerDTO(customer.getName(), customer.getEmail(), customer.getDocument(), null, null);
    }

    public Customer toCustomer() {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setDocument(document);
        return customer;
    }
}
