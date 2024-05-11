package com.desafio.audsat.dto;

import com.desafio.audsat.domain.Customer;
import com.desafio.audsat.domain.Driver;
import com.desafio.audsat.request.CustomerRequest;
import com.desafio.audsat.response.InsuranceResponse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

public record CustomerDTO(@NotNull String name, @NotNull @Email String email, @NotNull @CPF String document,
                          Driver driver, List<InsuranceResponse> insurances) {

    public static CustomerRequest of(Customer customer) {
        return new CustomerRequest(customer.getName(), customer.getEmail(), customer.getDocument());
    }

    public Customer toCustomer() {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setDocument(document);
        return customer;
    }
}
