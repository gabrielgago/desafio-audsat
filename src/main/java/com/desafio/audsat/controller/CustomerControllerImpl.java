package com.desafio.audsat.controller;

import com.desafio.audsat.controller.interfaces.CustomerController;
import com.desafio.audsat.domain.Customer;
import com.desafio.audsat.dto.CustomerDTO;
import com.desafio.audsat.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
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
    public static final String UPDATE_PATH = "update-costumer";
    public static final String DELETE_PATH = "delete-costumer";
    public static final String FIND_ALL_PATH = "find-all-costumers";

    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('SCOPE_write_insurance_company') or hasAnyAuthority('SCOPE_admin')")
    @Override
    public ResponseEntity<EntityModel<CustomerDTO>> createCustomer(@RequestBody @Valid CustomerDTO request) {
        log.info("Creating customer: {}", request);
        Customer customer = customerService.save(request.toCustomer());
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

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SCOPE_read_insurance_company') or hasAnyAuthority('SCOPE_admin')")
    @Override
    public ResponseEntity<EntityModel<CustomerDTO>> findCustomerById(@PathVariable @Min(0) Long id) {
        log.info("Find customer by id: {}", id);
        Customer customer = customerService.findById(id);
        EntityModel<CustomerDTO> resource = EntityModel.of(CustomerDTO.of(customer));

        resource.add(linkTo(methodOn(CustomerControllerImpl.class).findCustomerById(id)).withSelfRel(),
                linkTo(methodOn(CustomerControllerImpl.class).findAllCustomers()).withRel(FIND_ALL_PATH),
                linkTo(methodOn(CustomerControllerImpl.class).deleteCustomer(id)).withRel(DELETE_PATH),
                linkTo(methodOn(CustomerControllerImpl.class).updateCustomer(id, null)).withRel(UPDATE_PATH));

        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SCOPE_read_insurance_company') or hasAnyAuthority('SCOPE_admin')")
    @Override
    public ResponseEntity<Void> deleteCustomer(@PathVariable @Min(0) Long id) {
        log.info("Deleting customer by id: {}", id);
        customerService.deleteCustomer(id);
        return ResponseEntity
                .noContent()
                .header(HttpHeaders.LINK,
                        linkTo(methodOn(CustomerController.class)
                                .findAllCustomers())
                                .withRel(FIND_ALL_PATH)
                                .toString())
                .<Void>build();
    }

    @PutMapping("update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SCOPE_read_insurance_company') or hasAnyAuthority('SCOPE_admin')")
    @Override
    public ResponseEntity<EntityModel<CustomerDTO>> updateCustomer(@PathVariable @Min(0) Long id,
                                                                   @RequestBody @Valid CustomerDTO request) {
        log.info("Update customer by id: {}", id);
        Customer customerUpdated = customerService.updateCustomer(id, request.toCustomer());
        EntityModel<CustomerDTO> resource = EntityModel.of(CustomerDTO.of(customerUpdated));

        resource.add(linkTo(methodOn(CustomerControllerImpl.class).findCustomerById(id)).withRel(FIND_ONE_PATH),
                linkTo(methodOn(CustomerControllerImpl.class).findAllCustomers()).withRel(FIND_ALL_PATH),
                linkTo(methodOn(CustomerControllerImpl.class).deleteCustomer(id)).withRel(DELETE_PATH),
                linkTo(methodOn(CustomerControllerImpl.class).updateCustomer(id, null)).withSelfRel());
        return null;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SCOPE_read') or hasAnyAuthority('SCOPE_admin')")
    @Override
    public ResponseEntity<CollectionModel<CustomerDTO>> findAllCustomers() {
        log.info("Find all customers");

        List<Customer> customers = customerService.findAllCustomers();
        List<CustomerDTO> costomersDTO = customers.stream().map(CustomerDTO::of).toList();
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
