package com.desafio.audsat.service;

import com.desafio.audsat.domain.Car;
import com.desafio.audsat.exception.AudsatException;
import com.desafio.audsat.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CarService {

    private final CarRepository carRepository;

    /**
     * Search the car by id
     *
     * @param carId Car ID to be fetched
     * @return Saved Car
     */
    @Transactional(readOnly = true)
    public Car findById(Long carId) {
        log.info("Searching car with id: {}", carId);
        return carRepository.findById(carId).orElseThrow(() -> new AudsatException(MessageFormat.format("Car id {0} not found", carId)));
    }

    /**
     * Added a new car inside database for future queries
     *
     * @param car Car by create
     * @return Saved Car
     */
    @Transactional
    public Car save(Car car) {
        log.info("Creating car: {}", car);
        return carRepository.save(car);
    }

    /**
     * Find all cars saved inside database
     *
     * @return List by saved cars
     */
    @Transactional(readOnly = true)
    public List<Car> findAllCars() {
        return carRepository.findAll();
    }

    /**
     * Delete car by id
     *
     * @param id Car id used to delete resource
     */
    @Transactional
    public void deleteCar(Long id) {
        log.info("Deleting car with id: {}", id);
        carRepository.deleteById(id);
    }

    /**
     * Update saved car
     *
     * @param id  Car id to update
     * @param car Car with changes
     * @return Updated Car
     */
    @Transactional
    public Car updateCar(Long id,
                         Car car) {
        car.setId(id);
        return carRepository.save(car);
    }
}
