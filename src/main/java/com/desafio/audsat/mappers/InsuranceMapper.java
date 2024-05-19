package com.desafio.audsat.mappers;

import com.desafio.audsat.domain.Insurance;
import com.desafio.audsat.dto.InsuranceDTO;
import com.desafio.audsat.service.CarService;
import com.desafio.audsat.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InsuranceMapper implements Mapper<Insurance, InsuranceDTO> {

    private final CarService carService;
    private final CustomerService customerService;

    @Override
    public Insurance toEntity(InsuranceDTO dto) {
        return Insurance.builder()
                .car(carService.findById(dto.carId()))
                .customer(customerService.findById(dto.customerId()))
                .active(dto.isActive())
                .build();
    }

    @Override
    public InsuranceDTO fromEntity(Insurance entity) {
        return new InsuranceDTO(entity.getCar().getId(), entity.getCustomer().getId(), entity.isActive());
    }
}
