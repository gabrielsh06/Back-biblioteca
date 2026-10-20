package com.app.backbiblioteca.loan.dto.response;

public record MostBorrowedBookResponse(
        Long bookId,
        String title,
        long borrowCount
) {
}
