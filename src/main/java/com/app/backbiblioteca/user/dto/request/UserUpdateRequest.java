package com.app.backbiblioteca.user.dto.request;

public record UserUpdateRequest(
        String firstName,
        String lastName,
        String email
) {
}
