package com.desafio.audsat.factory;

import com.desafio.audsat.domain.Car;
import com.desafio.audsat.domain.Customer;
import com.desafio.audsat.domain.Insurance;

import java.time.LocalDateTime;
import java.util.Objects;

public class InsuranceFactory {

    private InsuranceFactory() {
    }

    public static Insurance buildInsuranceWithCustomerAndCar(Customer customer,
                                                             Car car) {
        Objects.requireNonNull(car, "Car cannot be null");
        Objects.requireNonNull(customer, "Customer cannot be null");
        Insurance insurance = new Insurance();
        insurance.setCustomer(customer);
        insurance.setCar(car);
        insurance.setActive(true);
        insurance.setCreatedDate(LocalDateTime.now());
        return insurance;
    }
}
