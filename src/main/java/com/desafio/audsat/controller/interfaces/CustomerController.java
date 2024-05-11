package com.desafio.audsat.controller.interfaces;

import com.desafio.audsat.dto.CustomerDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public interface CustomerController {

    @Operation(summary = "Create Customer", description = "This endpoint is by create customer")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Customer create with success", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @ExampleObject(value = """
            {
              "name": "Gabriel",
              "email": "gfrael@gmail.com",
              "document": "147.339.487-27"
            }
            """))), @ApiResponse(responseCode = "400", description = "Payload is wrong", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)), @ApiResponse(responseCode = "401", description = "JWT Token invalid", content = @Content(mediaType = "text/plain;charset=UTF-8"), headers = @Header(name = "WWW-Authenticate", description = "What was the problem with given token", example = "Bearer error=\"invalid_token\", error_description=\"An error occurred while attempting to decode the Jwt: Malformed token\", error_uri=\"https://tools.ietf.org/html/rfc6750#section-3.1\""))})
    ResponseEntity<EntityModel<CustomerDTO>> createCustomer(CustomerDTO request);
}
