package com.desafio.audsat.controller;

import com.desafio.audsat.ConfigurationTest;
import com.desafio.audsat.domain.Customer;
import com.desafio.audsat.domain.Driver;
import com.desafio.audsat.dto.CustomerDTO;
import com.desafio.audsat.mappers.CustomerMapper;
import com.desafio.audsat.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@ContextConfiguration(classes = {ConfigurationTest.class,
        AuthenticationTest.class,
        CustomerControllerImpl.class,
        CustomerMapper.class,})
@WebMvcTest(CustomerControllerImpl.class)
class CustomerControllerImplTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private AuthenticationTest authenticationTest;
    @MockBean
    private CustomerService customerService;
    @MockBean
    private CustomerMapper customerMapper;

    @Test
    void shouldCreateCustomer_WhenRequestIsCorrect() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO("Gabriel Martins", "gfrael@gmail.com", "147.339.487-27", 1L);
        Customer customer = new Customer(1L, "Gabriel Martins", "gfrael@gmail.com", "147.339.487-27", new Driver(), null);

        Mockito.when(customerMapper.toEntity(customerDTO)).thenReturn(customer);
        Mockito.when(customerService.save(Mockito.any(Customer.class))).thenReturn(customer);

        String authenticate = authenticationTest.authenticate();
        mvc.perform(post("/customer")
                        .header("Authorization", "Bearer " + authenticate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(customerDTO))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Gabriel Martins"))
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "http://localhost/customer/1"));
    }
}
