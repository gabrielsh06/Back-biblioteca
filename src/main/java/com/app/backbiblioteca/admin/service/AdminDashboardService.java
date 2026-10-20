package com.app.backbiblioteca.admin.service;

import com.app.backbiblioteca.admin.dto.response.AdminDashboardResponse;
import com.app.backbiblioteca.book.domain.Book;
import com.app.backbiblioteca.book.repository.BookRepository;
import com.app.backbiblioteca.user.repository.UserRepository;
import com.app.backbiblioteca.loan.domain.LoanStatus;
import com.app.backbiblioteca.loan.dto.response.MostBorrowedBookResponse;
import com.app.backbiblioteca.loan.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AdminDashboardService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final LoanRepository loanRepository;

    public AdminDashboardService(BookRepository bookRepository, UserRepository userRepository,
                                 LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
    }

    public AdminDashboardResponse getDashboard() {
        var activeBooks = bookRepository.findByActiveTrue();
        long totalStock = activeBooks.stream()
                .map(Book::getStock)
                .filter(stock -> stock != null)
                .mapToLong(Integer::longValue)
                .sum();

        long availableStock = activeBooks.stream()
                .filter(book -> book.getBookStatus() == com.app.backbiblioteca.book.domain.BookStatus.AVAILABLE
                        || book.getBookStatus() == com.app.backbiblioteca.book.domain.BookStatus.IN_LIBRARY)
                .map(Book::getStock)
                .filter(stock -> stock != null)
                .mapToLong(Integer::longValue)
                .sum();

        Map<String, Long> booksByStatus = activeBooks.stream()
                .map(Book::getBookStatus)
                .filter(status -> status != null)
                .map(Enum::name)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Map<String, Long> booksByOrigin = activeBooks.stream()
                .map(Book::getBookOrigin)
                .filter(origin -> origin != null)
                .map(Enum::name)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        long activeUsers = userRepository.countByActiveTrue();
        long inactiveUsers = userRepository.countByActiveFalse();

        return new AdminDashboardResponse(
                bookRepository.count(),
                bookRepository.countByActiveTrue(),
                bookRepository.countByActiveFalse(),
                totalStock,
                availableStock,
                activeUsers + inactiveUsers,
                activeUsers,
                inactiveUsers,
                booksByStatus,
                booksByOrigin,
                loanRepository.count(),
                loanRepository.countByStatus(LoanStatus.ACTIVE),
                loanRepository.countByStatus(LoanStatus.RETURNED),
                loanRepository.countByStatus(LoanStatus.OVERDUE),
                loanRepository.findMostBorrowed().stream()
                        .limit(10)
                        .map(row -> new MostBorrowedBookResponse(
                                ((Number) row[0]).longValue(),
                                (String) row[1],
                                ((Number) row[2]).longValue()))
                        .toList()
        );
    }
}
