package com.desafio.audsat.mappers;

import com.desafio.audsat.domain.Car;
import com.desafio.audsat.domain.CarDriver;
import com.desafio.audsat.domain.Insurance;
import com.desafio.audsat.dto.CarDTO;
import com.desafio.audsat.service.CarDriverService;
import com.desafio.audsat.service.InsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class CarMapper implements Mapper<Car, CarDTO> {

    private final CarDriverService carDriverService;
    private final InsuranceService insuranceService;

    @Override
    public Car toEntity(CarDTO dto) {
        Set<CarDriver> drivers = null;
        if (dto.carsDrivers() != null) {
            drivers = Set.copyOf(carDriverService.findAllById(dto.carsDrivers()));
        }

        Set<Insurance> insurances = null;
        if (dto.insurancesId() != null) {
            insurances = Set.copyOf(insuranceService.findAllById(dto.insurancesId()));
        }
        return Car.builder()
                .model(dto.model())
                .fipeValue(dto.fipeValue())
                .manufacturer(dto.manufacturer())
                .yearOfManufacturing(dto.year())
                .insurances(insurances)
                .carDrivers(drivers)
                .build();
    }

    @Override
    public CarDTO fromEntity(Car entity) {
        return new CarDTO(entity.getModel(), entity.getManufacturer(), entity.getYearOfManufacturing(), entity.getFipeValue(),
                entity.getCarDrivers().stream().mapToLong(CarDriver::getId).boxed().toList(),
                entity.getInsurances().stream().mapToLong(Insurance::getId).boxed().toList());
    }
}
