package com.desafio.audsat.domain;

import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.Instant;

public record StandardError(HttpStatus status,
                            String message,
                            Instant timestamp) implements Serializable {
}
