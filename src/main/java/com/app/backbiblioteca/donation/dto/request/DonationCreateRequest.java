package com.app.backbiblioteca.donation.dto.request;

import com.app.backbiblioteca.book.domain.Category;
import com.app.backbiblioteca.donation.domain.BookCondition;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DonationCreateRequest(
        @NotBlank(message = "El título es obligatorio") String title,
        @NotBlank(message = "El autor es obligatorio") String author,
        String ISBN,
        @NotNull(message = "La categoría es obligatoria") Category category,
        @NotNull(message = "La condición es obligatoria") BookCondition condition,
        @NotBlank(message = "El nombre del donante es obligatorio") String donorName,
        @NotBlank(message = "El correo del donante es obligatorio")
        @Email(message = "El correo del donante no es válido") String donorEmail,
        String notes
) {
}
