package com.app.backbiblioteca.book.repository;

import com.app.backbiblioteca.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    long countByActiveTrue();
    long countByActiveFalse();
    List<Book> findByActiveTrue();
}
