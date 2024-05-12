package com.desafio.audsat.domain;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public record DataConstraintsValidation(HttpStatus status, String message,
                                        Instant timestamp, String sql, String rejectedValue) {
}
