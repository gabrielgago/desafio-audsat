package com.desafio.audsat.controller.advices;

import com.desafio.audsat.component.MessageComponent;
import com.desafio.audsat.domain.DataConstraintsValidation;
import com.desafio.audsat.domain.StandardError;
import com.desafio.audsat.domain.ValidationStandardErrorImpl;
import com.desafio.audsat.exception.AudsatException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

@RequiredArgsConstructor
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    public static final Pattern DUPLICATE_KEY_PATTERN = Pattern.compile("VALUES \\(\\s.+?\\s'(.+?)'\\s\\)", Pattern.CASE_INSENSITIVE);
    public static final String ERROR_GENERIC = "error.generic";

    private final MessageComponent messageComponent;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<StandardError> handleAllExceptions(Exception ex,
                                                             WebRequest request) {
        log.error(messageComponent.getMessage(ERROR_GENERIC, ex.getMessage()), ex);
        return ResponseEntity
                .internalServerError()
                .body(StandardError.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .message(ex.getMessage())
                        .timestamp(Instant.now())
                        .build());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<DataConstraintsValidation> handleDataValidationExceptions(DataIntegrityViolationException ex,
                                                                                    WebRequest request) {
        log.error(messageComponent.getMessage(ERROR_GENERIC, ex.getMessage()));
        DataConstraintsValidation dataConstraintsValidation = null;
        if (ex.getCause() instanceof ConstraintViolationException) {
            dataConstraintsValidation = buildExceptionWithSql(ex, dataConstraintsValidation);
        } else {
            dataConstraintsValidation = new DataConstraintsValidation(NOT_ACCEPTABLE, ex.getMessage(), Instant.now(), null, null);
        }
        return ResponseEntity.status(BAD_REQUEST).body(dataConstraintsValidation);
    }

    private DataConstraintsValidation buildExceptionWithSql(DataIntegrityViolationException ex,
                                                            DataConstraintsValidation dataConstraintsValidation) {
        ConstraintViolationException cause = (ConstraintViolationException) ex.getCause();
        String sql = cause.getSQL();
        String message = cause.getMessage();
        Matcher matcher = DUPLICATE_KEY_PATTERN.matcher(message);
        if (matcher.find()) {
            String value = matcher.group(1);
            dataConstraintsValidation = new DataConstraintsValidation(NOT_ACCEPTABLE, message, Instant.now(), sql, value);
        }
        return dataConstraintsValidation;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationStandardErrorImpl> handleValidationExceptions(MethodArgumentNotValidException ex,
                                                                                  WebRequest request) {
        log.error(messageComponent.getMessage(ERROR_GENERIC, ex.getMessage()), ex);
        log.error(messageComponent.getMessage("validation.error.body", ex.getBody()));
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ValidationStandardErrorImpl.Field> fields = fieldErrors.stream().map(e -> new ValidationStandardErrorImpl.Field(e.getField(), e.getRejectedValue() != null ? e.getRejectedValue().toString() : "null", e.getDefaultMessage())).toList();
        ValidationStandardErrorImpl validationStandardError = new ValidationStandardErrorImpl(HttpStatus.BAD_REQUEST, ex.getBody().getDetail(), Instant.now(), fields);
        return ResponseEntity
                .internalServerError()
                .body(validationStandardError);
    }

    @ExceptionHandler(AudsatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<StandardError> handleBusinessExceptions(AudsatException ex) {
        log.error(messageComponent.getMessage(ERROR_GENERIC, ex.getMessage()), ex);
        return ResponseEntity
                .internalServerError()
                .body(StandardError.builder().status(BAD_REQUEST).message(ex.getMessage()).timestamp(Instant.now()).build());
    }

}
