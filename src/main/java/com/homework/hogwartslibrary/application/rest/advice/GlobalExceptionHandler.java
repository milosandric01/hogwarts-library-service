package com.homework.hogwartslibrary.application.rest.advice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(final IllegalArgumentException e) {
        final ErrorResponse error = new ErrorResponse(BAD_REQUEST.value(), e.getMessage());

        log.error("Illegal argument exception: {}", e.getMessage(), e);
        return new ResponseEntity<>(error, BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(final MethodArgumentTypeMismatchException e) {
        final ErrorResponse error = new ErrorResponse(BAD_REQUEST.value(), e.getMessage());

        log.error("Method type mismatch exception: {}", e.getMessage(), e);
        return new ResponseEntity<>(error, BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(final MethodArgumentNotValidException e) {
        Map<String, String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        DefaultMessageSourceResolvable::getDefaultMessage,
                        (existing, replacement) -> existing
                ));

        final ValidationErrorResponse error = new ValidationErrorResponse(
                BAD_REQUEST.value(),
                "Validation failed",
                errors
        );

        log.error("Argument not valid exception: {}", e.getMessage(), e);
        return new ResponseEntity<>(error, BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(HttpMessageNotReadableException e) {
        String errorDetails = "";

        if (e.getCause() instanceof InvalidFormatException) {
            InvalidFormatException ifx = (InvalidFormatException) e.getCause();
            errorDetails = String.format("Invalid value: '%s' for the field: '%s'. %s", ifx.getValue(), ifx.getPath().get(ifx.getPath().size()-1).getFieldName(), ifx.getOriginalMessage());
            if (ifx.getTargetType() != null && ifx.getTargetType().isEnum()) {
                errorDetails = String.format("Invalid value: '%s' for the field: '%s'. The value must be one of: %s.",
                        ifx.getValue(), ifx.getPath().get(ifx.getPath().size()-1).getFieldName(), Arrays.toString(ifx.getTargetType().getEnumConstants()));
            }

        }

        ErrorResponse errorResponse = new ErrorResponse(BAD_REQUEST.value(), errorDetails);

        log.error("Message not readable exception: {}", e.getMessage(), e);
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(final Exception e) {
        final ErrorResponse error = new ErrorResponse(INTERNAL_SERVER_ERROR.value(), "Something went wrong.");

        log.error("Internal service exception: {}", e.getMessage(), e);
        return new ResponseEntity<>(error, INTERNAL_SERVER_ERROR);
    }
}
