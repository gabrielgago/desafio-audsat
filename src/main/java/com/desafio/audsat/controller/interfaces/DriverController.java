package com.desafio.audsat.controller.interfaces;

import com.desafio.audsat.dto.DriverDTO;
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

public interface DriverController {

    @Operation(summary = "Allowed methods", description = "This endpoint lists the allowed methods")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "The allowed methods", content = @Content(examples = @ExampleObject(value = "GET,POST,PUT,DELETE,OPTIONS")), headers = @Header(name = "Allow", description = "What methods are allowed", example = "GET,POST,PUT,DELETE,OPTIONS")), @ApiResponse(responseCode = "401", description = "JWT Token invalid", content = @Content(mediaType = "text/plain;charset=UTF-8"), headers = @Header(name = "WWW-Authenticate", description = "What was the problem with given token", example = "Bearer error=\"invalid_token\", error_description=\"An error occurred while attempting to decode the Jwt: Malformed token\", error_uri=\"https://tools.ietf.org/html/rfc6750#section-3.1\""))})
    ResponseEntity<Object> options();

    @Operation(summary = "Create Driver", description = "This endpoint is by create driver", requestBody = @RequestBody(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DriverDTO.class))))
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Driver create with success", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @ExampleObject(value = """
            {
              "name": "Gabriel",
              "email": "gfrael@gmail.com",
              "document": "326.639.710-06"
            }
            """))), @ApiResponse(responseCode = "400", description = "Payload is wrong", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)), @ApiResponse(responseCode = "401", description = "JWT Token invalid", content = @Content(mediaType = "text/plain;charset=UTF-8"), headers = @Header(name = "WWW-Authenticate", description = "What was the problem with given token", example = "Bearer error=\"invalid_token\", error_description=\"An error occurred while attempting to decode the Jwt: Malformed token\", error_uri=\"https://tools.ietf.org/html/rfc6750#section-3.1\""))})
    ResponseEntity<EntityModel<DriverDTO>> createDriver(@org.springframework.web.bind.annotation.RequestBody @Valid DriverDTO request);

    @GetMapping("{id}")
    @Operation(summary = "Find Driver by ID", description = "This endpoint is by find driver by id", parameters = @Parameter(example = "1", description = "Id do driver to update"))
    @ApiResponses(value = {@ApiResponse(responseCode = "400", description = "Payload is wrong", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)), @ApiResponse(responseCode = "401", description = "JWT Token invalid", content = @Content(mediaType = "text/plain;charset=UTF-8"), headers = @Header(name = "WWW-Authenticate", description = "What was the problem with given token", example = "Bearer error=\"invalid_token\", error_description=\"An error occurred while attempting to decode the Jwt: Malformed token\", error_uri=\"https://tools.ietf.org/html/rfc6750#section-3.1\""))})
    ResponseEntity<EntityModel<DriverDTO>> findDriverById(@PathVariable @Min(0) Long id);

    @DeleteMapping("delete/{id}")
    @Operation(summary = "Delete Driver", description = "This endpoint is by delete driver", requestBody = @RequestBody(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = "1"))))
    @ApiResponses(value = {@ApiResponse(responseCode = "400", description = "Payload is wrong", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)), @ApiResponse(responseCode = "401", description = "JWT Token invalid", content = @Content(mediaType = "text/plain;charset=UTF-8"), headers = @Header(name = "WWW-Authenticate", description = "What was the problem with given token", example = "Bearer error=\"invalid_token\", error_description=\"An error occurred while attempting to decode the Jwt: Malformed token\", error_uri=\"https://tools.ietf.org/html/rfc6750#section-3.1\""))})
    ResponseEntity<Void> deleteDriver(@PathVariable @Min(0) Long id);

    @PutMapping("update/{id}")
    @Operation(summary = "Update Driver", description = "This endpoint is by update driver", parameters = @Parameter(example = "1", description = "Id do driver to update"), requestBody = @RequestBody(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DriverDTO.class))))
    @ApiResponses(value = {@ApiResponse(responseCode = "400", description = "Payload is wrong", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)), @ApiResponse(responseCode = "401", description = "JWT Token invalid", content = @Content(mediaType = "text/plain;charset=UTF-8"), headers = @Header(name = "WWW-Authenticate", description = "What was the problem with given token", example = "Bearer error=\"invalid_token\", error_description=\"An error occurred while attempting to decode the Jwt: Malformed token\", error_uri=\"https://tools.ietf.org/html/rfc6750#section-3.1\""))})
    ResponseEntity<EntityModel<DriverDTO>> updateDriver(@PathVariable @Min(0) Long id,
                                                        @org.springframework.web.bind.annotation.RequestBody @Valid DriverDTO request);

    @Operation(summary = "Find All Drivers", description = "This endpoint is by find all drivers")
    @ApiResponses(value = {@ApiResponse(responseCode = "400", description = "Payload is wrong", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)), @ApiResponse(responseCode = "401", description = "JWT Token invalid", content = @Content(mediaType = "text/plain;charset=UTF-8"), headers = @Header(name = "WWW-Authenticate", description = "What was the problem with given token", example = "Bearer error=\"invalid_token\", error_description=\"An error occurred while attempting to decode the Jwt: Malformed token\", error_uri=\"https://tools.ietf.org/html/rfc6750#section-3.1\""))})
    ResponseEntity<CollectionModel<DriverDTO>> findAllDrivers();
}
