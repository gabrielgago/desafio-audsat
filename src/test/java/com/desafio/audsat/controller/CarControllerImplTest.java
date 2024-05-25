package com.desafio.audsat.controller;

import com.desafio.audsat.ConfigurationTest;
import com.desafio.audsat.domain.Car;
import com.desafio.audsat.dto.CarDTO;
import com.desafio.audsat.mappers.CarMapper;
import com.desafio.audsat.service.CarService;
import com.desafio.audsat.utils.ResourceUtils;
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
        CarControllerImpl.class,
        CarMapper.class,})
@WebMvcTest(CarControllerImpl.class)
class CarControllerImplTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private AuthenticationTest authenticationTest;
    @MockBean
    private CarService carService;
    @MockBean
    private CarMapper carMapper;

    @Test
    void shouldCreateCar_WhenRequestIsCorrect() throws Exception {
        String payload = ResourceUtils.loadResourceAsString("examples/requests/request-car.json");
        CarDTO carDTO = mapper.readValue(payload, CarDTO.class);

        Car car = Car.builder().id(1L).model("Model S").manufacturer("Tesla").build();

        Mockito.when(carMapper.toEntity(carDTO)).thenReturn(car);
        Mockito.when(carService.save(Mockito.any(Car.class))).thenReturn(car);

        String authenticate = authenticationTest.authenticate();
        mvc.perform(post("/car")
                        .header("Authorization", "Bearer " + authenticate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(carDTO))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.model").value("Model S"))
                .andExpect(jsonPath("$.manufacturer").value("Tesla"))
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "http://localhost/car/1"));
    }
}