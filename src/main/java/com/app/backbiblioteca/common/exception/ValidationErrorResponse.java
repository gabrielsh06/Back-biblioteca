package com.app.backbiblioteca.common.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ValidationErrorResponse(
        int status,
        String error,
        Map<String, String> fieldErrors,
        LocalDateTime timestamp
) {
    public ValidationErrorResponse(int status, String error, Map<String, String> fieldErrors) {
        this(status, error, fieldErrors, LocalDateTime.now());
    }
}
