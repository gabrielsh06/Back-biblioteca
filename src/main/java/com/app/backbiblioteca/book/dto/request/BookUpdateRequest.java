package com.app.backbiblioteca.book.dto.request;

import com.app.backbiblioteca.book.domain.BookOrigin;
import com.app.backbiblioteca.book.domain.BookStatus;
import com.app.backbiblioteca.book.domain.Category;

import java.sql.Date;
import java.util.Set;

public record BookUpdateRequest(
        String title,
        String author,
        BookStatus bookStatus,
        BookOrigin bookOrigin,
        String coverUrl,
        String ISBN,
        String sku,
        String publisher,
        Date publishedDate,
        String edition,
        Integer pages,
        Integer stock,
        Set<Category> category
) {
}
