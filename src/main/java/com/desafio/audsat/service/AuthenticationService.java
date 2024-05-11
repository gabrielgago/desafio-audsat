package com.desafio.audsat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final JWTService jwtService;

    public String authenticate(Authentication authentication) {
        return jwtService.generateToken(authentication);
    }
}
