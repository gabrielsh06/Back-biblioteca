package com.app.backbiblioteca.book.dto.response;

import com.app.backbiblioteca.book.domain.Book;
import com.app.backbiblioteca.book.domain.BookOrigin;
import com.app.backbiblioteca.book.domain.BookStatus;
import com.app.backbiblioteca.book.domain.Category;

import java.sql.Date;
import java.util.Set;

public record BookResponse(
        Long id,
        String title,
        String author,
        BookStatus bookStatus,
        BookOrigin bookOrigin,
        Integer likesCount,
        String coverUrl,
        String ISBN,
        String sku,
        String publisher,
        Date publishedDate,
        String edition,
        Integer pages,
        Integer stock,
        Set<Category> category,
        Boolean active
) {
    public BookResponse(Book book) {
        this(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getBookStatus(),
                book.getBookOrigin(),
                book.getLikesCount(),
                book.getCoverUrl(),
                book.getISBN(),
                book.getSku(),
                book.getPublisher(),
                book.getPublishedDate(),
                book.getEdition(),
                book.getPages(),
                book.getStock(),
                book.getCategory(),
                book.getActive()
        );
    }
}
