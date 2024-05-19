package com.desafio.audsat.service;

import com.desafio.audsat.domain.CarDriver;
import com.desafio.audsat.exception.AudsatException;
import com.desafio.audsat.repository.CarDriverRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CarDriverService {

    private final CarDriverRepository carDriverRepository;

    /**
     * Search the carDriver by id
     *
     * @param carDriverId CarDriver ID to be fetched
     * @return Saved CarDriver
     */
    @Transactional(readOnly = true)
    public CarDriver findById(Long carDriverId) {
        log.info("Searching car driver with id: {}", carDriverId);
        return carDriverRepository.findById(carDriverId).orElseThrow(() -> new AudsatException(MessageFormat.format("Car Driver id {0} not found", carDriverId)));
    }

    /**
     * Added a new carDriver inside database for future queries
     *
     * @param carDriver CarDriver by create
     * @return Saved CarDriver
     */
    @Transactional
    public CarDriver save(CarDriver carDriver) {
        log.info("Creating car driver: {}", carDriver);
        return carDriverRepository.save(carDriver);
    }

    /**
     * Find all carDrivers saved inside database
     *
     * @return List by saved carDrivers
     */
    @Transactional(readOnly = true)
    public List<CarDriver> findAllCarDrivers() {
        log.info("Finding all car drivers");
        return carDriverRepository.findAll();
    }

    /**
     * Delete carDriver by id
     *
     * @param id CarDriver id used to delete resource
     */
    @Transactional
    public void deleteCarDriver(Long id) {
        log.info("Deleting carDriver with id: {}", id);
        carDriverRepository.deleteById(id);
    }

    /**
     * Update saved carDriver
     *
     * @param id        CarDriver id to update
     * @param carDriver CarDriver with changes
     * @return Updated CarDriver
     */
    @Transactional
    public CarDriver updateCarDriver(Long id,
                                     CarDriver carDriver) {
        log.info("Updating car driver with id: {}", id);
        carDriver.setId(id);
        return carDriverRepository.save(carDriver);
    }

    /**
     * Find all car drivers by id list
     *
     * @param ids CarDriver id to update
     * @return List of car drivers
     */
    @Transactional(readOnly = true)
    public List<CarDriver> findAllById(List<Long> ids) {
        log.info("Searching car drivers list by ids: {}", ids);
        return carDriverRepository.findAllById(ids);
    }
}
