package com.desafio.audsat.controller.interfaces;

import com.desafio.audsat.domain.Insurance;
import com.desafio.audsat.dto.InsuranceDTO;
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

public interface InsuranceController {

    @Operation(summary = "Allowed methods", description = "This endpoint lists the allowed methods")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The allowed methods", content = @Content(examples = @ExampleObject(value = "GET,POST,PUT,DELETE,OPTIONS")), headers = @Header(name = "Allow", description = "What methods are allowed", example = "GET,POST,PUT,DELETE,OPTIONS")),
            @ApiResponse(responseCode = "401", description = "JWT Token invalid", content = @Content(mediaType = "text/plain;charset=UTF-8"), headers = @Header(name = "WWW-Authenticate", description = "What was the problem with given token", example = "Bearer error=\"invalid_token\", error_description=\"An error occurred while attempting to decode the Jwt: Malformed token\", error_uri=\"https://tools.ietf.org/html/rfc6750#section-3.1\""))
    })
    ResponseEntity<Object> options();

    @Operation(summary = "Create Insurance", description = "This endpoint is by create insurance", requestBody = @RequestBody(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Insurance.class))))
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Insurance create with success", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @ExampleObject(value = """
            {
              "car_id": "1",
              "customer_id": "1",
              "is_active": "true"
            }
            """))), @ApiResponse(responseCode = "400", description = "Payload is wrong", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "401", description = "JWT Token invalid", content = @Content(mediaType = "text/plain;charset=UTF-8"), headers = @Header(name = "WWW-Authenticate", description = "What was the problem with given token", example = "Bearer error=\"invalid_token\", error_description=\"An error occurred while attempting to decode the Jwt: Malformed token\", error_uri=\"https://tools.ietf.org/html/rfc6750#section-3.1\""))
    })
    ResponseEntity<EntityModel<InsuranceDTO>> createInsurance(InsuranceDTO request);

    @Operation(summary = "Find All Insurances", description = "This endpoint is by find all insurances")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Payload is wrong", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "401", description = "JWT Token invalid", content = @Content(mediaType = "text/plain;charset=UTF-8"), headers = @Header(name = "WWW-Authenticate", description = "What was the problem with given token", example = "Bearer error=\"invalid_token\", error_description=\"An error occurred while attempting to decode the Jwt: Malformed token\", error_uri=\"https://tools.ietf.org/html/rfc6750#section-3.1\""))
    })
    ResponseEntity<CollectionModel<InsuranceDTO>> findAllInsurances();

    @GetMapping("{id}")
    @Operation(summary = "Find Insurance by ID", description = "This endpoint is by find insurance by id", parameters = @Parameter(example = "1", description = "Id do insurance to update"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Payload is wrong", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "401", description = "JWT Token invalid", content = @Content(mediaType = "text/plain;charset=UTF-8"), headers = @Header(name = "WWW-Authenticate", description = "What was the problem with given token", example = "Bearer error=\"invalid_token\", error_description=\"An error occurred while attempting to decode the Jwt: Malformed token\", error_uri=\"https://tools.ietf.org/html/rfc6750#section-3.1\""))
    })
    ResponseEntity<EntityModel<InsuranceDTO>> findInsuranceById(Long id);

    @DeleteMapping("delete/{id}")
    @Operation(summary = "Delete Insurance", description = "This endpoint is by delete insurance", requestBody = @RequestBody(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = "1"))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Payload is wrong", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "401", description = "JWT Token invalid", content = @Content(mediaType = "text/plain;charset=UTF-8"), headers = @Header(name = "WWW-Authenticate", description = "What was the problem with given token", example = "Bearer error=\"invalid_token\", error_description=\"An error occurred while attempting to decode the Jwt: Malformed token\", error_uri=\"https://tools.ietf.org/html/rfc6750#section-3.1\""))
    })
    ResponseEntity<Void> deleteInsuranceById(Long id);

    @PutMapping("update/{id}")
    @Operation(summary = "Update Insurance", description = "This endpoint is by update insurance", parameters = @Parameter(example = "1", description = "Id do costumer to update"), requestBody = @RequestBody(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = InsuranceDTO.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Payload is wrong", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "401", description = "JWT Token invalid", content = @Content(mediaType = "text/plain;charset=UTF-8"), headers = @Header(name = "WWW-Authenticate", description = "What was the problem with given token", example = "Bearer error=\"invalid_token\", error_description=\"An error occurred while attempting to decode the Jwt: Malformed token\", error_uri=\"https://tools.ietf.org/html/rfc6750#section-3.1\""))
    })
    ResponseEntity<EntityModel<InsuranceDTO>> updateInsurance(@PathVariable @Min(0) Long id,
                                                              @org.springframework.web.bind.annotation.RequestBody @Valid InsuranceDTO request);
}
