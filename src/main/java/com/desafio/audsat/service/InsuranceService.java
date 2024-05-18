package com.desafio.audsat.service;

import com.desafio.audsat.component.MessageComponent;
import com.desafio.audsat.domain.Car;
import com.desafio.audsat.domain.Customer;
import com.desafio.audsat.domain.Insurance;
import com.desafio.audsat.dto.InsuranceDTO;
import com.desafio.audsat.exception.AudsatException;
import com.desafio.audsat.factory.InsuranceFactory;
import com.desafio.audsat.repository.InsuranceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;
    private final CarService carService;
    private final CustomerService customerService;
    private final MessageComponent messageComponent;

    public Insurance createInsurance(InsuranceDTO insuranceDTO) {
        log.info("Init create insurance: {}", insuranceDTO);
        Insurance insurance = toInsurance(insuranceDTO);
        insurance.setCreationDate(LocalDateTime.now());
        insurance.setActive(true);
        insurance = insuranceRepository.save(insurance);
        log.info("Insurance created with id: {}", insurance.getId());
        return insurance;
    }

    public Set<Insurance> findAllInsurances() {
        log.info("Find all insurances");
        List<Insurance> insurances = insuranceRepository.findAll();
        return Set.copyOf(insurances);
    }

    public Insurance findById(Long id) {
        log.info("Searching insurance with id: {}", id);
        return insuranceRepository.findById(id).orElseThrow(() -> new AudsatException(messageComponent.getMessage("error.insurance.not-found", id)));
    }

    public void deleteInsurance(Long id) {
        log.info("Deleting insurance with id: {}", id);
        insuranceRepository.deleteById(id);
    }

    private Insurance toInsurance(InsuranceDTO dto) {
        Car car = carService.findById(dto.carId());
        Customer customer = customerService.findById(dto.costumerId());
        return InsuranceFactory.buildInsuranceWithCustomerAndCar(customer, car);
    }

    public Insurance updateInsurance(Long id,
                                     InsuranceDTO dto) {
        log.info("Updating insurance with id: {}", id);
        Insurance insurance = toInsurance(dto);
        insurance.setId(id);
        insurance.setUpdatedDate(LocalDateTime.now());
        return insuranceRepository.save(insurance);
    }
}
