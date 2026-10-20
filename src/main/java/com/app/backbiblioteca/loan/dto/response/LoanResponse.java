package com.app.backbiblioteca.loan.dto.response;

import com.app.backbiblioteca.loan.domain.Loan;
import com.app.backbiblioteca.loan.domain.LoanStatus;

import java.time.LocalDate;

public record LoanResponse(
        Long id,
        Long userId,
        String userEmail,
        Long bookId,
        String bookTitle,
        LocalDate loanDate,
        LocalDate dueDate,
        LocalDate returnedDate,
        LoanStatus status
) {
    public LoanResponse(Loan loan) {
        this(
                loan.getId(),
                loan.getUser().getId(),
                loan.getUser().getEmail(),
                loan.getBook().getId(),
                loan.getBook().getTitle(),
                loan.getLoanDate(),
                loan.getDueDate(),
                loan.getReturnedDate(),
                loan.getStatus()
        );
    }
}
