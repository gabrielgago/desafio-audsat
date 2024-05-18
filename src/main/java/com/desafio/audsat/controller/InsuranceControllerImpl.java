package com.desafio.audsat.controller;

import com.desafio.audsat.controller.interfaces.InsuranceController;
import com.desafio.audsat.domain.Insurance;
import com.desafio.audsat.dto.InsuranceDTO;
import com.desafio.audsat.service.InsuranceService;
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
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping("insurance")
@RequiredArgsConstructor
public class InsuranceControllerImpl implements InsuranceController {

    public static final String ALL_INSURANCES = "all-insurances";
    public static final String FIND_ONE_PATH = "find-insurance";
    public static final String UPDATE_PATH = "update-insurance";
    public static final String DELETE_PATH = "delete-insurance";

    private final InsuranceService insuranceService;

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
    public ResponseEntity<EntityModel<InsuranceDTO>> createInsurance(InsuranceDTO request) {
        log.info("Creating insurance: {}", request);
        Insurance insurance = insuranceService.createInsurance(request);
        EntityModel<InsuranceDTO> resource = EntityModel.of(request);

        final String uriString = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(insurance.getId())
                .toUriString();

        resource.add(linkTo(methodOn(InsuranceControllerImpl.class).findInsuranceById(insurance.getId())).withRel(FIND_ONE_PATH),
                linkTo(methodOn(InsuranceControllerImpl.class).findAllInsurances()).withRel(ALL_INSURANCES),
                linkTo(methodOn(InsuranceControllerImpl.class).deleteInsuranceById(insurance.getId())).withRel(DELETE_PATH),
                linkTo(methodOn(InsuranceControllerImpl.class).updateInsurance(insurance.getId(), null)).withRel(UPDATE_PATH));

        return ResponseEntity.created(URI.create(uriString)).body(resource);
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SCOPE_read_insurance_company') or hasAnyAuthority('SCOPE_admin')")
    @Override
    public ResponseEntity<EntityModel<InsuranceDTO>> findInsuranceById(Long id) {
        log.info("Find insurance by id: {}", id);
        Insurance insurance = insuranceService.findById(id);
        EntityModel<InsuranceDTO> resource = EntityModel.of(InsuranceDTO.from(insurance));

        resource.add(linkTo(methodOn(InsuranceControllerImpl.class).findInsuranceById(id)).withSelfRel(),
                linkTo(methodOn(InsuranceControllerImpl.class).findAllInsurances()).withRel(ALL_INSURANCES),
                linkTo(methodOn(InsuranceControllerImpl.class).deleteInsuranceById(id)).withRel(DELETE_PATH),
                linkTo(methodOn(InsuranceControllerImpl.class).updateInsurance(id, null)).withRel(UPDATE_PATH));

        return ResponseEntity.ok(resource);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SCOPE_write_insurance_company') or hasAnyAuthority('SCOPE_admin')")
    @Override
    public ResponseEntity<Void> deleteInsuranceById(Long id) {
        log.info("Deleting insurance by id: {}", id);
        insuranceService.deleteInsurance(id);
        return ResponseEntity
                .noContent()
                .<Void>build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SCOPE_write_insurance_company') or hasAnyAuthority('SCOPE_admin')")
    @Override
    public ResponseEntity<EntityModel<InsuranceDTO>> updateInsurance(Long id,
                                                                     InsuranceDTO request) {
        log.info("Update insurance by id: {}", id);
        Insurance insuranceUpdated = insuranceService.updateInsurance(id, request);
        EntityModel<InsuranceDTO> resource = EntityModel.of(InsuranceDTO.from(insuranceUpdated));

        resource.add(linkTo(methodOn(InsuranceControllerImpl.class).findInsuranceById(id)).withRel(FIND_ONE_PATH),
                linkTo(methodOn(InsuranceControllerImpl.class).findAllInsurances()).withRel(ALL_INSURANCES),
                linkTo(methodOn(InsuranceControllerImpl.class).deleteInsuranceById(id)).withRel(DELETE_PATH),
                linkTo(methodOn(InsuranceControllerImpl.class).updateInsurance(id, null)).withSelfRel());
        return null;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SCOPE_read_insurance_company') or hasAnyAuthority('SCOPE_admin')")
    @Override
    public ResponseEntity<CollectionModel<InsuranceDTO>> findAllInsurances() {
        log.info("Find all insurances");

        Set<Insurance> insurances = insuranceService.findAllInsurances();
        CollectionModel<InsuranceDTO> resources = CollectionModel.of(insurances.stream().map(InsuranceDTO::from).toList());
        List<Link> links = insurances.stream().map(insurance -> List.of(linkTo(methodOn(InsuranceControllerImpl.class).findInsuranceById(insurance.getId())).withRel(FIND_ONE_PATH),
                        linkTo(methodOn(InsuranceControllerImpl.class).deleteInsuranceById(insurance.getId())).withRel(DELETE_PATH),
                        linkTo(methodOn(InsuranceControllerImpl.class).updateInsurance(insurance.getId(), null)).withRel(UPDATE_PATH)))
                .reduce(new ArrayList<>(), (agr, list) -> {
                    agr.addAll(list);
                    return agr;
                });

        resources.add(links);
        resources.add(linkTo(methodOn(InsuranceControllerImpl.class).findAllInsurances()).withSelfRel());

        return ResponseEntity.ok(resources);
    }
}
