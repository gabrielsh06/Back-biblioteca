package com.app.backbiblioteca.book.repository;

import com.app.backbiblioteca.book.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
  
    long countByActiveTrue();
    long countByActiveFalse();
    List<Book> findByActiveTrue();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select b from Book b where b.id = :id")
    Optional<Book> findByIdForUpdate(@Param("id") Long id);
  
    @Query("""
            SELECT b FROM Book b
            WHERE b.active = true
              AND (
                  :search IS NULL OR :search = ''
                  OR LOWER(b.title) LIKE LOWER(CONCAT('%', :search, '%'))
                  OR LOWER(b.author) LIKE LOWER(CONCAT('%', :search, '%'))
                  OR LOWER(b.ISBN) LIKE LOWER(CONCAT('%', :search, '%'))
                  OR LOWER(b.publisher) LIKE LOWER(CONCAT('%', :search, '%'))
              )
              AND (
                  :category IS NULL OR :category = ''
                  OR LOWER(FUNCTION('text', b.category)) LIKE LOWER(CONCAT('%', :category, '%'))
              )
            """)
    Page<Book> searchBooks(
            @Param("search") String search,
            @Param("category") String category,
            Pageable pageable
    );
}
