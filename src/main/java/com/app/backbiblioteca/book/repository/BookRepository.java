package com.app.backbiblioteca.book.repository;

import com.app.backbiblioteca.book.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {

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
