package com.desafio.audsat.service;

import com.desafio.audsat.domain.Customer;
import com.desafio.audsat.exception.AudsatException;
import com.desafio.audsat.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    /**
     * Search the customer by id
     *
     * @param customerId Customer ID to be fetched
     * @return Saved Customer
     */
    @Transactional(readOnly = true)
    public Customer findById(Long customerId) {
        log.info("Searching customer with id: {}", customerId);
        return customerRepository.findById(customerId).orElseThrow(() -> new AudsatException(MessageFormat.format("Customer id {0} not found", customerId)));
    }

    /**
     * Added a new customer inside database for future queries
     *
     * @param customer Customer by create
     * @return Saved Customer
     */
    @Transactional
    public Customer save(Customer customer) {
        log.info("Creating customer: {}", customer);
        return customerRepository.save(customer);
    }

    /**
     * Find all customers saved inside database
     *
     * @return List by saved customers
     */
    @Transactional(readOnly = true)
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * Delete customer by id
     *
     * @param id Customer id used to delete resource
     */
    @Transactional
    public void deleteCustomer(Long id) {
        log.info("Deleting customer with id: {}", id);
        customerRepository.deleteById(id);
    }

    /**
     * Update saved customer
     *
     * @param id       Customer id to update
     * @param customer Customer with changes
     * @return Updated Customer
     */
    @Transactional
    public Customer updateCustomer(Long id,
                                   Customer customer) {
        customer.setId(id);
        return customerRepository.save(customer);
    }
}
