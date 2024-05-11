package com.desafio.audsat.controller;

import com.desafio.audsat.controller.interfaces.CustomerController;
import com.desafio.audsat.domain.Customer;
import com.desafio.audsat.dto.CustomerDTO;
import com.desafio.audsat.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/customer")
public class CustomerControllerImpl implements CustomerController {

    public static final String ALL_CUSTOMERS = "all-customers";

    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    @PreAuthorize("hasAnyAuthority('SCOPE_write_insurance_company') or hasAnyAuthority('SCOPE_admin')")
    public ResponseEntity<EntityModel<CustomerDTO>> createCustomer(@RequestBody @Valid CustomerDTO request) {
        log.info("Creating customer: {}", request);
        Customer customer = customerService.save(request.toCustomer());
        EntityModel<CustomerDTO> resource = EntityModel.of(request);

        final String uriString = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(customer.getId())
                .toUriString();

        resource.add(linkTo(methodOn(CustomerControllerImpl.class).findCustomerById(customer.getId())).withSelfRel());
        return ResponseEntity.created(URI.create(uriString)).body(resource);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
//    @Override
    @PreAuthorize("hasAnyAuthority('SCOPE_read_insurance_company') or hasAnyAuthority('SCOPE_admin')")
    public ResponseEntity<EntityModel<CustomerDTO>> findCustomerById(@PathVariable Long id) {
        log.info("Find customer by id: {}", id);
        return null;
    }
}
