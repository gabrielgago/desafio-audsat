package com.desafio.audsat.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@AllArgsConstructor
@Builder
@Data
public class StandardError {
    private HttpStatus status;
    private String message;
    private Instant timestamp;
}
