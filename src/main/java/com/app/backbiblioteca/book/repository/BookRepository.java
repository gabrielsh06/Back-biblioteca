package com.app.backbiblioteca.book.repository;

import com.app.backbiblioteca.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
