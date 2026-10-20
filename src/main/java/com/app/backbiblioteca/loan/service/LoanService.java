package com.app.backbiblioteca.loan.service;

import com.app.backbiblioteca.book.domain.Book;
import com.app.backbiblioteca.book.domain.BookStatus;
import com.app.backbiblioteca.book.repository.BookRepository;
import com.app.backbiblioteca.loan.domain.Loan;
import com.app.backbiblioteca.loan.domain.LoanStatus;
import com.app.backbiblioteca.loan.dto.request.LoanCreateRequest;
import com.app.backbiblioteca.loan.dto.response.LoanResponse;
import com.app.backbiblioteca.loan.repository.LoanRepository;
import com.app.backbiblioteca.user.domain.User;
import com.app.backbiblioteca.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {

    private static final List<LoanStatus> OPEN_STATUSES = List.of(LoanStatus.ACTIVE, LoanStatus.OVERDUE);
    private static final int LOAN_DURATION_DAYS = 14;

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public LoanService(LoanRepository loanRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public LoanResponse create(LoanCreateRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .filter(User::isActive)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Book book = bookRepository.findByIdForUpdate(request.bookId())
                .filter(Book::getActive)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        if (book.getStock() == null || book.getStock() <= 0) {
            throw new IllegalStateException("Book is not available");
        }
        if (loanRepository.existsByUserIdAndBookIdAndStatusIn(userId, book.getId(), OPEN_STATUSES)) {
            throw new IllegalStateException("User already has an active loan for this book");
        }

        book.setStock(book.getStock() - 1);
        if (book.getStock() == 0 && book.getBookStatus() != BookStatus.UNAVAILABLE) {
            book.setBookStatus(BookStatus.CHECKED_OUT);
        }

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(LOAN_DURATION_DAYS));
        loan.setStatus(LoanStatus.ACTIVE);
        bookRepository.save(book);
        return new LoanResponse(loanRepository.save(loan));
    }

    @Transactional
    public LoanResponse returnLoan(Long loanId, Long userId, boolean admin) {
        Loan loan = admin
                ? loanRepository.findById(loanId)
                .orElseThrow(() -> new EntityNotFoundException("Loan not found"))
                : loanRepository.findByIdAndUserId(loanId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Loan not found"));

        if (loan.getStatus() == LoanStatus.RETURNED) {
            throw new IllegalStateException("Loan has already been returned");
        }

        Book book = loan.getBook();
        book.setStock((book.getStock() == null ? 0 : book.getStock()) + 1);
        if (book.getBookStatus() == BookStatus.CHECKED_OUT) {
            book.setBookStatus(BookStatus.AVAILABLE);
        }
        loan.setReturnedDate(LocalDate.now());
        loan.setStatus(LoanStatus.RETURNED);
        bookRepository.save(book);
        return new LoanResponse(loanRepository.save(loan));
    }

    @Transactional(readOnly = true)
    public List<LoanResponse> findMine(Long userId) {
        return loanRepository.findByUserIdOrderByLoanDateDesc(userId).stream()
                .map(LoanResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<LoanResponse> findAll() {
        return loanRepository.findAllByOrderByLoanDateDesc().stream()
                .map(LoanResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<LoanResponse> findOverdue() {
        return loanRepository.findByStatusInAndDueDateBefore(
                        List.of(LoanStatus.ACTIVE, LoanStatus.OVERDUE), LocalDate.now())
                .stream()
                .map(LoanResponse::new)
                .toList();
    }

    @Transactional
    public int markOverdue() {
        return loanRepository.markOverdue(LoanStatus.ACTIVE, LoanStatus.OVERDUE, LocalDate.now());
    }
}
