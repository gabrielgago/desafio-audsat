package com.desafio.audsat.controller.advices.models;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public record DataConstraintsValidation(HttpStatus status, String message,
                                        Instant timestamp, String sql, String rejectedValue) {
}
