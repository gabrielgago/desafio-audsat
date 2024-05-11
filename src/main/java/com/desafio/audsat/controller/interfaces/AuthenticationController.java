package com.desafio.audsat.controller.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.core.Authentication;

public interface AuthenticationController {

    @Operation(summary = "Authenticate", description = "Get JWT token to authenticate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "JWT token", content = @Content(mediaType = "text/plain;charset=UTF-8")),
            @ApiResponse(responseCode = "401", description = "Username and password not found", content = @Content(mediaType = "text/plain;charset=UTF-8"))
    })
    String authenticate(Authentication authentication);
}
