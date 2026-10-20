package com.app.backbiblioteca.admin.service;

import com.app.backbiblioteca.admin.dto.response.AdminDashboardResponse;
import com.app.backbiblioteca.book.domain.Book;
import com.app.backbiblioteca.book.repository.BookRepository;
import com.app.backbiblioteca.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AdminDashboardService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public AdminDashboardService(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
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
                booksByOrigin
        );
    }
}
