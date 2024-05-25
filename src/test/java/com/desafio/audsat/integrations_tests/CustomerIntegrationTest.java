package com.desafio.audsat.integrations_tests;

import com.desafio.audsat.domain.Customer;
import com.desafio.audsat.domain.Driver;
import com.desafio.audsat.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class CustomerIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @WithMockUser(username = "testuser")
    void whenCreateCustomer_thenAuditingFieldsAreSet() {
        Driver driver = new Driver();
        driver.setBirthdate(LocalDate.now());
        driver.setDocument("111");

        Customer customer = Customer.builder()
                .email("gfrael@gmail.com")
                .document("147.339.487-27")
                .name("Gabriel Martins")
                .driver(driver)
                .build();

        Customer savedCustomer = customerRepository.save(customer);

        assertThat(savedCustomer.getCreatedBy()).isEqualTo("testuser");
        assertThat(savedCustomer.getCreationDate()).isNotNull();
        assertThat(savedCustomer.getLastModifiedBy()).isEqualTo("testuser");
        assertThat(savedCustomer.getLastModifiedDate()).isNotNull();
    }
}
