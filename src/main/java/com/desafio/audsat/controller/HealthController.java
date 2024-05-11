package com.desafio.audsat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("public")
public class HealthController {

    @Operation(summary = "Health", description = "To know if app is online")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Is online"),
            @ApiResponse(responseCode = "500", description = "Is offline")
    })
    @GetMapping(path = "health", produces = "application/json")
    public ResponseEntity<Object> health() {
        return ResponseEntity.noContent().build();
    }
}

