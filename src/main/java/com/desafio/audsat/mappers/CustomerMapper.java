package com.desafio.audsat.mappers;

import com.desafio.audsat.domain.Customer;
import com.desafio.audsat.domain.Driver;
import com.desafio.audsat.dto.CustomerDTO;
import com.desafio.audsat.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomerMapper implements Mapper<Customer, CustomerDTO> {

    private final DriverService driverService;

    @Override
    public Customer toEntity(CustomerDTO dto) {
        Driver driver = driverService.findById(dto.driverId());
        return Customer.builder()
                .email(dto.email())
                .document(dto.document())
                .name(dto.name())
                .driver(driver)
                .build();
    }

    @Override
    public CustomerDTO fromEntity(Customer entity) {
        return new CustomerDTO(entity.getName(), entity.getEmail(), entity.getDocument(), entity.getDriver() != null ? entity.getDriver().getId() : null);
    }
}
