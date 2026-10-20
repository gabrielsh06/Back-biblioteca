package com.app.backbiblioteca.user.dto.response;

import com.app.backbiblioteca.user.domain.Roles;
import com.app.backbiblioteca.user.domain.User;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        Roles roles,
        String email,
        String password,
        boolean state
) {
    public UserResponse(User user) {
        this(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole(),
                user.getEmail(),
                user.getPassword(),
                user.isActive());
    }
}
