package com.app.backbiblioteca.loan.dto.request;

import jakarta.validation.constraints.NotNull;

public record LoanCreateRequest(
        @NotNull(message = "Book id is required")
        Long bookId
) {
}
