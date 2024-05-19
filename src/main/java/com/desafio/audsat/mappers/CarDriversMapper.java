package com.desafio.audsat.mappers;

import com.desafio.audsat.domain.CarDriver;
import com.desafio.audsat.dto.CarDriverDTO;
import com.desafio.audsat.service.CarService;
import com.desafio.audsat.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarDriversMapper implements Mapper<CarDriver, CarDriverDTO> {

    private final DriverService driverService;
    private final CarService carService;

    @Override
    public CarDriver toEntity(CarDriverDTO dto) {
        return CarDriver.builder()
                .driver(driverService.findById(dto.driverId()))
                .car(carService.findById(dto.carId()))
                .isMainDriver(dto.isMainDriver())
                .build();
    }

    @Override
    public CarDriverDTO fromEntity(CarDriver entity) {
        return new CarDriverDTO(entity.getDriver().getId(), entity.getCar().getId(), entity.getIsMainDriver());
    }
}
