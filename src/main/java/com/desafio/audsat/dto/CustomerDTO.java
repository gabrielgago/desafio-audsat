package com.desafio.audsat.dto;

import com.desafio.audsat.domain.Customer;
import com.desafio.audsat.domain.Driver;
import com.desafio.audsat.domain.Insurance;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Set;

public record CustomerDTO(@NotNull String name, @NotNull @Email String email, @NotNull @CPF String document,
                          Driver driver, Set<Insurance> insurances) {

    public static CustomerDTO of(Customer customer) {
        return new CustomerDTO(customer.getName(), customer.getEmail(), customer.getDocument(), customer.getDriver(), customer.getInsurances());
    }

    public Customer toCustomer() {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setDocument(document);
        return customer;
    }
}
