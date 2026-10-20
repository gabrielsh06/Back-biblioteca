package com.app.backbiblioteca.admin.service;

import com.app.backbiblioteca.admin.dto.response.AdminDashboardResponse;
import com.app.backbiblioteca.book.domain.Book;
import com.app.backbiblioteca.book.domain.BookOrigin;
import com.app.backbiblioteca.book.domain.BookStatus;
import com.app.backbiblioteca.book.repository.BookRepository;
import com.app.backbiblioteca.loan.domain.LoanStatus;
import com.app.backbiblioteca.loan.repository.LoanRepository;
import com.app.backbiblioteca.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminDashboardServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LoanRepository loanRepository;

    private AdminDashboardService service;

    @BeforeEach
    void setUp() {
        service = new AdminDashboardService(bookRepository, userRepository, loanRepository);
    }

    @Test
    void shouldCalculateDashboardTotalsAndGroups() {
        var available = book(1L, "Clean Code", BookStatus.AVAILABLE, BookOrigin.PURCHASED, 4);
        var inLibrary = book(2L, "The Pragmatic Programmer", BookStatus.IN_LIBRARY,
                BookOrigin.COMMUNITY_DONATION, 3);
        var checkedOut = book(3L, "Effective Java", BookStatus.CHECKED_OUT,
                BookOrigin.PURCHASED, 2);
        var missingValues = book(4L, "Unknown", null, null, null);
        when(bookRepository.findByActiveTrue()).thenReturn(List.of(available, inLibrary, checkedOut, missingValues));
        when(bookRepository.count()).thenReturn(8L);
        when(bookRepository.countByActiveTrue()).thenReturn(4L);
        when(bookRepository.countByActiveFalse()).thenReturn(4L);
        when(userRepository.countByActiveTrue()).thenReturn(6L);
        when(userRepository.countByActiveFalse()).thenReturn(2L);
        when(loanRepository.count()).thenReturn(10L);
        when(loanRepository.countByStatus(LoanStatus.ACTIVE)).thenReturn(3L);
        when(loanRepository.countByStatus(LoanStatus.RETURNED)).thenReturn(5L);
        when(loanRepository.countByStatus(LoanStatus.OVERDUE)).thenReturn(2L);
        when(loanRepository.findMostBorrowed()).thenReturn(List.<Object[]>of(
                new Object[]{2L, "The Pragmatic Programmer", 7L}
        ));

        AdminDashboardResponse response = service.getDashboard();

        assertThat(response.totalBooks()).isEqualTo(8L);
        assertThat(response.activeBooks()).isEqualTo(4L);
        assertThat(response.inactiveBooks()).isEqualTo(4L);
        assertThat(response.totalStock()).isEqualTo(9L);
        assertThat(response.availableStock()).isEqualTo(7L);
        assertThat(response.totalUsers()).isEqualTo(8L);
        assertThat(response.activeUsers()).isEqualTo(6L);
        assertThat(response.inactiveUsers()).isEqualTo(2L);
        assertThat(response.booksByStatus())
                .containsEntry("AVAILABLE", 1L)
                .containsEntry("IN_LIBRARY", 1L)
                .containsEntry("CHECKED_OUT", 1L)
                .doesNotContainKey(null);
        assertThat(response.booksByOrigin())
                .containsEntry("PURCHASED", 2L)
                .containsEntry("COMMUNITY_DONATION", 1L)
                .doesNotContainKey(null);
        assertThat(response.totalLoans()).isEqualTo(10L);
        assertThat(response.activeLoans()).isEqualTo(3L);
        assertThat(response.returnedLoans()).isEqualTo(5L);
        assertThat(response.overdueLoans()).isEqualTo(2L);
        assertThat(response.mostBorrowedBooks()).singleElement()
                .satisfies(book -> {
                    assertThat(book.bookId()).isEqualTo(2L);
                    assertThat(book.title()).isEqualTo("The Pragmatic Programmer");
                    assertThat(book.borrowCount()).isEqualTo(7L);
                });
    }

    @Test
    void shouldLimitMostBorrowedBooksToTen() {
        when(bookRepository.findByActiveTrue()).thenReturn(List.of());
        when(userRepository.countByActiveTrue()).thenReturn(0L);
        when(userRepository.countByActiveFalse()).thenReturn(0L);
        List<Object[]> mostBorrowedRows = java.util.stream.IntStream.range(0, 12)
                        .mapToObj(index -> new Object[]{(long) index, "Book " + index, (long) index})
                        .toList();
        when(loanRepository.findMostBorrowed()).thenReturn(mostBorrowedRows);

        AdminDashboardResponse response = service.getDashboard();

        assertThat(response.mostBorrowedBooks()).hasSize(10);
        assertThat(response.mostBorrowedBooks().get(9).bookId()).isEqualTo(9L);
    }

    @Test
    void shouldReturnEmptyCollectionsWhenThereIsNoActiveData() {
        when(bookRepository.findByActiveTrue()).thenReturn(List.of());
        when(userRepository.countByActiveTrue()).thenReturn(0L);
        when(userRepository.countByActiveFalse()).thenReturn(0L);
        when(loanRepository.findMostBorrowed()).thenReturn(List.of());

        AdminDashboardResponse response = service.getDashboard();

        assertThat(response.totalStock()).isZero();
        assertThat(response.availableStock()).isZero();
        assertThat(response.booksByStatus()).isEmpty();
        assertThat(response.booksByOrigin()).isEmpty();
        assertThat(response.mostBorrowedBooks()).isEmpty();
    }

    @Test
    void shouldQueryLoanCountsForEveryDashboardStatus() {
        when(bookRepository.findByActiveTrue()).thenReturn(List.of());
        when(userRepository.countByActiveTrue()).thenReturn(0L);
        when(userRepository.countByActiveFalse()).thenReturn(0L);
        when(loanRepository.findMostBorrowed()).thenReturn(List.of());

        service.getDashboard();

        verify(loanRepository).countByStatus(LoanStatus.ACTIVE);
        verify(loanRepository).countByStatus(LoanStatus.RETURNED);
        verify(loanRepository).countByStatus(LoanStatus.OVERDUE);
    }

    private Book book(Long id, String title, BookStatus status, BookOrigin origin, Integer stock) {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setBookStatus(status);
        book.setBookOrigin(origin);
        book.setStock(stock);
        return book;
    }
}
