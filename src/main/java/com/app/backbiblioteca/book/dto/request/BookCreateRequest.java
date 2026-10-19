package com.app.backbiblioteca.book.dto.request;

import com.app.backbiblioteca.book.domain.Book;
import com.app.backbiblioteca.book.domain.BookOrigin;
import com.app.backbiblioteca.book.domain.BookStatus;
import com.app.backbiblioteca.book.domain.Category;

import java.sql.Date;
import java.util.Set;

public record BookCreateRequest(
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
    public Book toEntity() {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setBookStatus(bookStatus);
        book.setBookOrigin(bookOrigin);
        book.setCoverUrl(coverUrl);
        book.setISBN(ISBN);
        book.setSku(sku);
        book.setPublisher(publisher);
        book.setPublishedDate(publishedDate);
        book.setEdition(edition);
        book.setPages(pages);
        book.setStock(stock);
        book.setCategory(category);
        book.setLikesCount(0);

        return book;
    }
}
