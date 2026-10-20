package com.app.backbiblioteca.loan.repository;

import com.app.backbiblioteca.loan.domain.Loan;
import com.app.backbiblioteca.loan.domain.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    boolean existsByUserIdAndBookIdAndStatusIn(Long userId, Long bookId, List<LoanStatus> statuses);
    List<Loan> findByUserIdOrderByLoanDateDesc(Long userId);
    List<Loan> findAllByOrderByLoanDateDesc();
    long countByStatus(LoanStatus status);
    List<Loan> findByStatusInAndDueDateBefore(List<LoanStatus> statuses, LocalDate date);

    @Modifying
    @Query("update Loan l set l.status = :overdue where l.status = :active and l.dueDate < :today")
    int markOverdue(@Param("active") LoanStatus active,
                    @Param("overdue") LoanStatus overdue,
                    @Param("today") LocalDate today);

    @Query("""
            select l.book.id, l.book.title, count(l)
            from Loan l
            group by l.book.id, l.book.title
            order by count(l) desc
            """)
    List<Object[]> findMostBorrowed();

    Optional<Loan> findByIdAndUserId(Long id, Long userId);
}
