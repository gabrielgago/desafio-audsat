package com.desafio.audsat.mappers;

import com.desafio.audsat.domain.CarDriver;
import com.desafio.audsat.domain.Customer;
import com.desafio.audsat.domain.Driver;
import com.desafio.audsat.dto.DriverDTO;
import com.desafio.audsat.repository.CarDriverRepository;
import com.desafio.audsat.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class DriverMapper implements Mapper<Driver, DriverDTO> {

    private final CarDriverRepository carDriverRepository;
    private final CustomerRepository customerRepository;

    @Override
    public Driver toEntity(DriverDTO dto) {
        Set<CarDriver> cars = null;
        if (dto.carsDrivers() != null) {
            cars = Set.copyOf(carDriverRepository.findAllById(dto.carsDrivers().stream().toList()));
        }
        Set<Customer> customers = null;
        if (dto.costumers() != null) {
            customers = Set.copyOf(customerRepository.findAllById(dto.costumers().stream().toList()));
        }
        return Driver.builder().birthdate(dto.birthdate()).document(dto.document()).carDrivers(cars).customers(customers).build();
    }

    @Override
    public DriverDTO fromEntity(Driver entity) {
        return new DriverDTO(entity.getDocument(),
                entity.getBirthdate(),
                entity.getCarDrivers().stream().mapToLong(CarDriver::getId).boxed().collect(Collectors.toList()),
                entity.getCustomers().stream().mapToLong(Customer::getId).boxed().collect(Collectors.toList()));
    }
}
