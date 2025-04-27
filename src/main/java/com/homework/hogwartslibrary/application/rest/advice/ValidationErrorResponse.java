package com.homework.hogwartslibrary.application.rest.advice;

import lombok.Data;
import lombok.Getter;

import java.util.Map;

@Getter
public class ValidationErrorResponse extends ErrorResponse {
    private Map<String, String> errors;

    public ValidationErrorResponse(int status, String message, Map<String, String> errors) {
        super(status, message);
        this.errors = errors;
    }
}
