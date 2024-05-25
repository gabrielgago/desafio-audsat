package com.desafio.audsat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final JWTService jwtService;

    public String authenticate(Authentication authentication) {
        log.info("Token will be generate to user {}", authentication.getName());
        return jwtService.generateToken(authentication);
    }
}
