package com.desafio.audsat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("public")
public class HealthController {

    @GetMapping(path = "health", produces = "application/json")
    public ResponseEntity<Object> health() {
        return ResponseEntity.noContent().build();
    }
}

