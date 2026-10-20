package com.app.backbiblioteca.book.dto.response;

import java.sql.Date;

public record BookLookupResponse(
        String title,
        String author,
        String coverUrl,
        String ISBN,
        String publisher,
        Date publishedDate,
        Integer pages
) {
}
