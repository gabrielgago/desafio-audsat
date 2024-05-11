package com.desafio.audsat.controller.advices;

import com.desafio.audsat.domain.StandardError;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<StandardError> handleAllExceptions(Exception ex,
                                                             WebRequest request) {
        log.error("There was an error, message: {}.", ex.getMessage(), ex);
        return ResponseEntity
                .internalServerError()
                .body(StandardError.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .message(ex.getMessage())
                        .timestamp(Instant.now())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<StandardError> handleValidationExceptions(MethodArgumentNotValidException ex,
                                                             WebRequest request) {
        log.error("There was an error, message: {}.", ex.getMessage());
        log.error("There was an error, body: {}.", ex.getBody());
        return ResponseEntity
                .internalServerError()
                .body(StandardError.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message(ex.getBody().getDetail())
                        .timestamp(Instant.now())
                        .build());
    }
}
