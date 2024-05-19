package com.desafio.audsat.service;

import com.desafio.audsat.domain.Driver;
import com.desafio.audsat.exception.AudsatException;
import com.desafio.audsat.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class DriverService {

    private final DriverRepository driverRepository;

    /**
     * Search the driver by id
     *
     * @param driverId Driver ID to be fetched
     * @return Saved Driver
     */
    @Transactional(readOnly = true)
    public Driver findById(Long driverId) {
        log.info("Searching driver with id: {}", driverId);
        return driverRepository.findById(driverId).orElseThrow(() -> new AudsatException(MessageFormat.format("Driver id {0} not found", driverId)));
    }

    /**
     * Added a new driver inside database for future queries
     *
     * @param driver Driver by create
     * @return Saved Driver
     */
    @Transactional
    public Driver save(Driver driver) {
        log.info("Creating driver: {}", driver);
        return driverRepository.save(driver);
    }

    /**
     * Find all drivers saved inside database
     *
     * @return List by saved drivers
     */
    @Transactional(readOnly = true)
    public List<Driver> findAllDrivers() {
        return driverRepository.findAll();
    }

    /**
     * Delete driver by id
     *
     * @param id Driver id used to delete resource
     */
    @Transactional
    public void deleteDriver(Long id) {
        log.info("Deleting driver with id: {}", id);
        driverRepository.deleteById(id);
    }

    /**
     * Update saved driver
     *
     * @param id     Driver id to update
     * @param driver Driver with changes
     * @return Updated Driver
     */
    @Transactional
    public Driver updateDriver(Long id,
                               Driver driver) {
        driver.setId(id);
        return driverRepository.save(driver);
    }
}
