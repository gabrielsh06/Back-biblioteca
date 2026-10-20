package com.app.backbiblioteca.book.dto.request;

import com.app.backbiblioteca.book.domain.BookOrigin;
import com.app.backbiblioteca.book.domain.BookStatus;
import com.app.backbiblioteca.book.domain.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;
import java.util.Set;

public record BookUpdateRequest(
        @NotBlank(message = "Title cannot be blank")
        String title,

        @NotBlank(message = "Author cannot be blank")
        String author,

        BookStatus bookStatus,
        BookOrigin bookOrigin,
        String coverUrl,

        @NotBlank(message = "ISBN cannot be blank")
        String ISBN,

        String sku,
        String publisher,
        Date publishedDate,
        String edition,

        @Min(value = 1, message = "Pages must be greater than 0")
        Integer pages,

        @NotNull(message = "Stock is required")
        @Min(value = 0, message = "Stock cannot be negative")
        Integer stock,

        @NotEmpty(message = "At least one category is required")
        Set<Category> category
) {
}
