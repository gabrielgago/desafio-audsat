package com.desafio.audsat.controller.interfaces;

import com.desafio.audsat.dto.CarDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

public interface CarController {

    @Operation(summary = "Allowed methods", description = "This endpoint lists the allowed methods")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The allowed methods", content = @Content(examples = @ExampleObject(value = "GET,POST,PUT,DELETE,OPTIONS")), headers = @Header(name = "Allow", description = "What methods are allowed", example = "GET,POST,PUT,DELETE,OPTIONS")),
            @ApiResponse(responseCode = "401", description = "JWT Token invalid", content = @Content(mediaType = "text/plain;charset=UTF-8"), headers = @Header(name = "WWW-Authenticate", description = "What was the problem with given token", example = "Bearer error=\"invalid_token\", error_description=\"An error occurred while attempting to decode the Jwt: Malformed token\", error_uri=\"https://tools.ietf.org/html/rfc6750#section-3.1\""))
    })
    ResponseEntity<Object> options();

    @Operation(summary = "Create Car", description = "This endpoint is by create car", requestBody = @RequestBody(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CarDTO.class))))
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Car create with success", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @ExampleObject(description = "Payload to create a car", value = """
              {
              "model": "Model S",
              "manufacturer": "Tesla",
              "year": "2020",
              "fipeValue": 75000.00
            }
            """))), @ApiResponse(responseCode = "400", description = "Payload is wrong", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "401", description = "JWT Token invalid", content = @Content(mediaType = "text/plain;charset=UTF-8"), headers = @Header(name = "WWW-Authenticate", description = "What was the problem with given token", example = "Bearer error=\"invalid_token\", error_description=\"An error occurred while attempting to decode the Jwt: Malformed token\", error_uri=\"https://tools.ietf.org/html/rfc6750#section-3.1\""))
    })
    ResponseEntity<EntityModel<CarDTO>> createCar(@org.springframework.web.bind.annotation.RequestBody @Valid CarDTO request);

    @GetMapping("{id}")
    @Operation(summary = "Find Car by ID", description = "This endpoint is by find car by id", parameters = @Parameter(example = "1", description = "Id do car to find"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Payload is wrong", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "401", description = "JWT Token invalid", content = @Content(mediaType = "text/plain;charset=UTF-8"), headers = @Header(name = "WWW-Authenticate", description = "What was the problem with given token", example = "Bearer error=\"invalid_token\", error_description=\"An error occurred while attempting to decode the Jwt: Malformed token\", error_uri=\"https://tools.ietf.org/html/rfc6750#section-3.1\"")),
            @ApiResponse(responseCode = "200", description = "The car finded", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @ExampleObject(value = """
                    {
                       "model": "Model S",
                       "manufacturer": "Tesla",
                       "year": "2020",
                       "fipeValue": 75000.00,
                       "carsDrivers": [1, 2],
                        "insurancesId": [101, 102]
                     }
                    """)))
    })
    ResponseEntity<EntityModel<CarDTO>> findCarById(@PathVariable @Min(0) Long id);

    @DeleteMapping("delete/{id}")
    @Operation(summary = "Delete Car", description = "This endpoint is by delete car", requestBody = @RequestBody(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = "1"))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Payload is wrong", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "401", description = "JWT Token invalid", content = @Content(mediaType = "text/plain;charset=UTF-8"), headers = @Header(name = "WWW-Authenticate", description = "What was the problem with given token", example = "Bearer error=\"invalid_token\", error_description=\"An error occurred while attempting to decode the Jwt: Malformed token\", error_uri=\"https://tools.ietf.org/html/rfc6750#section-3.1\""))
    })
    ResponseEntity<Void> deleteCar(@PathVariable @Min(0) Long id);

    @PutMapping("update/{id}")
    @Operation(summary = "Update Car", description = "This endpoint is by update car", parameters = @Parameter(example = "1", description = "Id do car to update"), requestBody = @RequestBody(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CarDTO.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Payload is wrong", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "401", description = "JWT Token invalid", content = @Content(mediaType = "text/plain;charset=UTF-8"), headers = @Header(name = "WWW-Authenticate", description = "What was the problem with given token", example = "Bearer error=\"invalid_token\", error_description=\"An error occurred while attempting to decode the Jwt: Malformed token\", error_uri=\"https://tools.ietf.org/html/rfc6750#section-3.1\""))
    })
    ResponseEntity<EntityModel<CarDTO>> updateCar(@PathVariable @Min(0) Long id,
                                                  @org.springframework.web.bind.annotation.RequestBody @Valid CarDTO request);

    @Operation(summary = "Find All Cars", description = "This endpoint is by find all cars")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Payload is wrong", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "401", description = "JWT Token invalid", content = @Content(mediaType = "text/plain;charset=UTF-8"), headers = @Header(name = "WWW-Authenticate", description = "What was the problem with given token", example = "Bearer error=\"invalid_token\", error_description=\"An error occurred while attempting to decode the Jwt: Malformed token\", error_uri=\"https://tools.ietf.org/html/rfc6750#section-3.1\"")),
            @ApiResponse(responseCode = "200", description = "The car finded", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @ExampleObject(value = """
                    [
                        {
                           "model": "Model S",
                           "manufacturer": "Tesla",
                           "year": "2020",
                           "fipeValue": 75000.00,
                           "carsDrivers": [1, 2],
                            "insurancesId": [101, 102]
                         }
                     ]
                    """)))
    })
    ResponseEntity<CollectionModel<CarDTO>> findAllCars();
}
