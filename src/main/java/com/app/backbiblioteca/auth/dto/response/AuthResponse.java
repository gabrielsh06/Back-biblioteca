package com.app.backbiblioteca.auth.dto.response;

import com.app.backbiblioteca.user.domain.Roles;

public record AuthResponse(
        String token,
        String email,
        String firstName,
        String lastName,
        Roles role
) {
}
