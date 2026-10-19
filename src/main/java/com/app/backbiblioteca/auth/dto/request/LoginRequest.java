package com.app.backbiblioteca.auth.dto.request;

public record LoginRequest(
        String email,
        String password
) {
}
