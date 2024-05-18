package com.desafio.audsat.factory;

import com.desafio.audsat.domain.Car;
import com.desafio.audsat.domain.Customer;
import com.desafio.audsat.domain.Insurance;

import java.time.LocalDateTime;

public class InsuranceFactory {

    private InsuranceFactory() {
    }

    public static Insurance buildInsuranceWithCustomerAndCar(Customer customer,
                                                             Car car) {
        Insurance insurance = new Insurance();
        insurance.setCustomer(customer);
        insurance.setCar(car);
        insurance.setActive(true);
        insurance.setCreationDate(LocalDateTime.now());
        return insurance;
    }
}
