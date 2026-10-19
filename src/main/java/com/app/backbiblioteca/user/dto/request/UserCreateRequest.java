package com.app.backbiblioteca.user.dto.request;

import com.app.backbiblioteca.user.domain.Roles;
import com.app.backbiblioteca.user.domain.User;

public record UserCreateRequest(
        String firstName,
        String lastName,
        Roles role,
        String email,
        String password
) {
    public User toEntity() {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole(role);
        user.setEmail(email);
        user.setPassword(password);

        return user;
    }
}
