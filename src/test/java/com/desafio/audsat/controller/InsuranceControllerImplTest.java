package com.desafio.audsat.controller;

import com.desafio.audsat.ConfigurationTest;
import com.desafio.audsat.domain.Car;
import com.desafio.audsat.domain.Customer;
import com.desafio.audsat.domain.Insurance;
import com.desafio.audsat.dto.InsuranceDTO;
import com.desafio.audsat.factory.InsuranceFactory;
import com.desafio.audsat.mappers.CustomerMapper;
import com.desafio.audsat.mappers.InsuranceMapper;
import com.desafio.audsat.service.InsuranceService;
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
        InsuranceControllerImpl.class,
        InsuranceMapper.class,})
@WebMvcTest(InsuranceControllerImpl.class)
class InsuranceControllerImplTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private AuthenticationTest authenticationTest;
    @MockBean
    private InsuranceService insuranceService;
    @MockBean
    private InsuranceMapper insuranceMapper;

    @Test
    void shouldCreateInsurance_WhenRequestIsCorrect() throws Exception {
        InsuranceDTO insuranceDTO = new InsuranceDTO(1L, 1L, true);
        Insurance insurance = InsuranceFactory.buildInsuranceWithCustomerAndCar(new Customer(), new Car());
        insurance.setId(1L);

        Mockito.when(insuranceMapper.toEntity(insuranceDTO)).thenReturn(insurance);
        Mockito.when(insuranceService.createInsurance(insuranceDTO)).thenReturn(insurance);

        String authenticate = authenticationTest.authenticate();
        mvc.perform(post("/insurance")
                        .header("Authorization", "Bearer " + authenticate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(insuranceDTO))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.car_id").value("1"))
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "http://localhost/insurance/1"));
    }
}