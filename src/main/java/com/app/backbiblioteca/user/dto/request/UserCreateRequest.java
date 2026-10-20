package com.app.backbiblioteca.user.dto.request;

import com.app.backbiblioteca.user.domain.Roles;
import com.app.backbiblioteca.user.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateRequest(
        @NotBlank(message = "First name cannot be blank")
        String firstName,

        @NotBlank(message = "Last name cannot be blank")
        String lastName,

        Roles role,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Must be a valid email address")
        String email,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 6, message = "Password must have at least 6 characters")
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
