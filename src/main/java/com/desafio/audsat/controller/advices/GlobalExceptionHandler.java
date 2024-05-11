package com.desafio.audsat.controller.advices;

import com.desafio.audsat.component.MessageComponent;
import com.desafio.audsat.domain.StandardError;
import com.desafio.audsat.domain.ValidationStandardErrorImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageComponent messageComponent;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<StandardError> handleAllExceptions(Exception ex,
                                                             WebRequest request) {
        log.error(messageComponent.getMessage("error.generic", ex.getMessage()), ex);
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
    public ResponseEntity<ValidationStandardErrorImpl> handleValidationExceptions(MethodArgumentNotValidException ex,
                                                                                  WebRequest request) {
        log.error(messageComponent.getMessage("error.generic", ex.getMessage()), ex);
        log.error(messageComponent.getMessage("validation.error.body", ex.getBody()));
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ValidationStandardErrorImpl.Field> fields = fieldErrors.stream().map(e -> new ValidationStandardErrorImpl.Field(e.getField(), e.getRejectedValue() != null ? e.getRejectedValue().toString() : "null", e.getDefaultMessage())).toList();
        ValidationStandardErrorImpl validationStandardError = new ValidationStandardErrorImpl(HttpStatus.BAD_REQUEST, ex.getBody().getDetail(), Instant.now(), fields);
        return ResponseEntity
                .internalServerError()
                .body(validationStandardError);
    }
}
