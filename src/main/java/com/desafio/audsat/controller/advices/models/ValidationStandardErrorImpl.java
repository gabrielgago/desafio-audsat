package com.desafio.audsat.controller.advices.models;

import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;

public record ValidationStandardErrorImpl(HttpStatus status, String message,
                                          Instant timestamp, List<Field> fields) {
    public static record Field(String field, String valueRejected, String message) {
    }
}
