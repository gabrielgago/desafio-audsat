package com.desafio.audsat.controller;

import com.desafio.audsat.controller.interfaces.CustomerController;
import com.desafio.audsat.domain.Customer;
import com.desafio.audsat.dto.CustomerDTO;
import com.desafio.audsat.mappers.CustomerMapper;
import com.desafio.audsat.service.CustomerService;
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
@RequestMapping("/customer")
public class CustomerControllerImpl implements CustomerController {

    public static final String FIND_ONE_PATH = "find-customer";
    public static final String UPDATE_PATH = "update-customer";
    public static final String DELETE_PATH = "delete-customer";
    public static final String FIND_ALL_PATH = "find-all-customers";

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

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
    public ResponseEntity<EntityModel<CustomerDTO>> createCustomer(CustomerDTO request) {
        log.info("Creating customer: {}", request);
        Customer customer = customerMapper.toEntity(request);
        customerService.save(customer);
        EntityModel<CustomerDTO> resource = EntityModel.of(request);

        final String uriString = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(customer.getId())
                .toUriString();

        resource.add(linkTo(methodOn(CustomerControllerImpl.class).findCustomerById(customer.getId())).withRel(FIND_ONE_PATH),
                linkTo(methodOn(CustomerControllerImpl.class).findAllCustomers()).withRel(FIND_ALL_PATH),
                linkTo(methodOn(CustomerControllerImpl.class).deleteCustomer(customer.getId())).withRel(DELETE_PATH),
                linkTo(methodOn(CustomerControllerImpl.class).updateCustomer(customer.getId(), null)).withRel(UPDATE_PATH));

        return ResponseEntity.created(URI.create(uriString)).body(resource);
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SCOPE_read_insurance_company') or hasAnyAuthority('SCOPE_admin')")
    @Override
    public ResponseEntity<EntityModel<CustomerDTO>> findCustomerById(Long id) {
        log.info("Find customer by id: {}", id);
        Customer customer = customerService.findById(id);
        EntityModel<CustomerDTO> resource = EntityModel.of(customerMapper.fromEntity(customer));

        resource.add(linkTo(methodOn(CustomerControllerImpl.class).findCustomerById(id)).withSelfRel(),
                linkTo(methodOn(CustomerControllerImpl.class).findAllCustomers()).withRel(FIND_ALL_PATH),
                linkTo(methodOn(CustomerControllerImpl.class).deleteCustomer(id)).withRel(DELETE_PATH),
                linkTo(methodOn(CustomerControllerImpl.class).updateCustomer(id, null)).withRel(UPDATE_PATH));

        return ResponseEntity.ok(resource);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SCOPE_write_insurance_company') or hasAnyAuthority('SCOPE_admin')")
    @Override
    public ResponseEntity<Void> deleteCustomer(Long id) {
        log.info("Deleting customer by id: {}", id);
        customerService.deleteCustomer(id);
        return ResponseEntity
                .noContent()
                .<Void>build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SCOPE_write_insurance_company') or hasAnyAuthority('SCOPE_admin')")
    @Override
    public ResponseEntity<EntityModel<CustomerDTO>> updateCustomer(Long id,
                                                                   CustomerDTO request) {
        log.info("Update customer by id: {}", id);
        Customer customer = customerMapper.toEntity(request);
        Customer customerUpdated = customerService.updateCustomer(id, customer);
        EntityModel<CustomerDTO> resource = EntityModel.of(customerMapper.fromEntity(customerUpdated));

        resource.add(linkTo(methodOn(CustomerControllerImpl.class).findCustomerById(id)).withRel(FIND_ONE_PATH),
                linkTo(methodOn(CustomerControllerImpl.class).findAllCustomers()).withRel(FIND_ALL_PATH),
                linkTo(methodOn(CustomerControllerImpl.class).deleteCustomer(id)).withRel(DELETE_PATH),
                linkTo(methodOn(CustomerControllerImpl.class).updateCustomer(id, null)).withSelfRel());
        return null;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SCOPE_read_insurance_company') or hasAnyAuthority('SCOPE_admin')")
    @Override
    public ResponseEntity<CollectionModel<CustomerDTO>> findAllCustomers() {
        log.info("Find all customers");

        List<Customer> customers = customerService.findAllCustomers();
        List<CustomerDTO> costomersDTO = customers.stream().map(customerMapper::fromEntity).toList();
        CollectionModel<CustomerDTO> resources = CollectionModel.of(costomersDTO);
        List<Link> links = customers.stream().map(c -> List.of(linkTo(methodOn(CustomerControllerImpl.class).findCustomerById(c.getId())).withRel(FIND_ONE_PATH),
                        linkTo(methodOn(CustomerControllerImpl.class).deleteCustomer(c.getId())).withRel(DELETE_PATH),
                        linkTo(methodOn(CustomerControllerImpl.class).updateCustomer(c.getId(), null)).withRel(UPDATE_PATH)))
                .reduce(new ArrayList<>(), (agr, list) -> {
                    agr.addAll(list);
                    return agr;
                });

        resources.add(links);
        resources.add(linkTo(methodOn(CustomerControllerImpl.class).findAllCustomers()).withSelfRel());

        return ResponseEntity.ok(resources);
    }
}
