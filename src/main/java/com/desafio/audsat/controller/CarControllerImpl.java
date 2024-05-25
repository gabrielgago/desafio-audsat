package com.desafio.audsat.controller;

import com.desafio.audsat.controller.interfaces.CarController;
import com.desafio.audsat.domain.Car;
import com.desafio.audsat.dto.CarDTO;
import com.desafio.audsat.mappers.CarMapper;
import com.desafio.audsat.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/car")
public class CarControllerImpl implements CarController {

    public static final String FIND_ONE_PATH = "find-car";
    public static final String UPDATE_PATH = "update-car";
    public static final String DELETE_PATH = "delete-car";
    public static final String FIND_ALL_PATH = "find-all-cars";

    private final CarService carService;
    private final CarMapper carMapper;

    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<Object> options() {
        return ResponseEntity
                .ok()
                .allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.OPTIONS)
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('SCOPE_write_insurance_company') or hasAnyAuthority('SCOPE_admin')")
    @Override
    public ResponseEntity<EntityModel<CarDTO>> createCar(@RequestBody @Valid CarDTO request) {
        log.info("Creating car: {}", request);
        Car car = carMapper.toEntity(request);
        carService.save(car);
        EntityModel<CarDTO> resource = EntityModel.of(request);

        final String uriString = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(car.getId())
                .toUriString();

        resource.add(linkTo(methodOn(CarControllerImpl.class).findCarById(car.getId())).withRel(FIND_ONE_PATH),
                linkTo(methodOn(CarControllerImpl.class).findAllCars()).withRel(FIND_ALL_PATH),
                linkTo(methodOn(CarControllerImpl.class).deleteCar(car.getId())).withRel(DELETE_PATH),
                linkTo(methodOn(CarControllerImpl.class).updateCar(car.getId(), null)).withRel(UPDATE_PATH));

        return ResponseEntity.created(URI.create(uriString)).body(resource);
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SCOPE_read_insurance_company') or hasAnyAuthority('SCOPE_admin')")
    @Override
    public ResponseEntity<EntityModel<CarDTO>> findCarById(Long id) {
        log.info("Find car by id: {}", id);
        Car car = carService.findById(id);
        EntityModel<CarDTO> resource = EntityModel.of(carMapper.fromEntity(car));

        resource.add(linkTo(methodOn(CarControllerImpl.class).findCarById(id)).withSelfRel(),
                linkTo(methodOn(CarControllerImpl.class).findAllCars()).withRel(FIND_ALL_PATH),
                linkTo(methodOn(CarControllerImpl.class).deleteCar(id)).withRel(DELETE_PATH),
                linkTo(methodOn(CarControllerImpl.class).updateCar(id, null)).withRel(UPDATE_PATH));

        return ResponseEntity.ok(resource);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SCOPE_write_insurance_company') or hasAnyAuthority('SCOPE_admin')")
    @Override
    public ResponseEntity<Void> deleteCar(Long id) {
        log.info("Deleting car by id: {}", id);
        carService.deleteCar(id);
        return ResponseEntity
                .noContent()
                .<Void>build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SCOPE_write_insurance_company') or hasAnyAuthority('SCOPE_admin')")
    @Override
    public ResponseEntity<EntityModel<CarDTO>> updateCar(Long id,
                                                                   @RequestBody @Valid CarDTO request) {
        log.info("Update car by id: {}", id);
        Car car = carMapper.toEntity(request);
        Car carUpdated = carService.updateCar(id, car);
        EntityModel<CarDTO> resource = EntityModel.of(carMapper.fromEntity(carUpdated));

        resource.add(linkTo(methodOn(CarControllerImpl.class).findCarById(id)).withRel(FIND_ONE_PATH),
                linkTo(methodOn(CarControllerImpl.class).findAllCars()).withRel(FIND_ALL_PATH),
                linkTo(methodOn(CarControllerImpl.class).deleteCar(id)).withRel(DELETE_PATH),
                linkTo(methodOn(CarControllerImpl.class).updateCar(id, null)).withSelfRel());
        return null;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SCOPE_read_insurance_company') or hasAnyAuthority('SCOPE_admin')")
    @Override
    public ResponseEntity<CollectionModel<CarDTO>> findAllCars() {
        log.info("Find all cars");

        List<Car> cars = carService.findAllCars();
        List<CarDTO> costomersDTO = cars.stream().map(carMapper::fromEntity).toList();
        CollectionModel<CarDTO> resources = CollectionModel.of(costomersDTO);
        List<Link> links = cars.stream().map(c -> List.of(linkTo(methodOn(CarControllerImpl.class).findCarById(c.getId())).withRel(FIND_ONE_PATH),
                        linkTo(methodOn(CarControllerImpl.class).deleteCar(c.getId())).withRel(DELETE_PATH),
                        linkTo(methodOn(CarControllerImpl.class).updateCar(c.getId(), null)).withRel(UPDATE_PATH)))
                .reduce(new ArrayList<>(), (agr, list) -> {
                    agr.addAll(list);
                    return agr;
                });

        resources.add(links);
        resources.add(linkTo(methodOn(CarControllerImpl.class).findAllCars()).withSelfRel());

        return ResponseEntity.ok(resources);
    }
}
