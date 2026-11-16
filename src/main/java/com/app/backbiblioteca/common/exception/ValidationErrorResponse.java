package com.app.backbiblioteca.common.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ValidationErrorResponse(
        int status,
        String error,
        String message,
        Map<String, String> errors,
        LocalDateTime timestamp
) {
    public ValidationErrorResponse(int status, String error, String message, Map<String, String> errors) {
        this(status, error, message, errors, LocalDateTime.now());
    }
}
