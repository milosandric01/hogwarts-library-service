package com.homework.hogwartslibrary.application.rest.advice;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ErrorResponse {
    private final int status;
    private final String message;
}