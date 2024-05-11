package com.desafio.audsat.controller.advices;

import com.desafio.audsat.domain.StandardError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

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
                        .timestamp(LocalDateTime.now())
                        .stackTrace(ex.getStackTrace())
                        .build());
    }
}
