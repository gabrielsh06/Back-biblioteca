package com.app.backbiblioteca.book.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {
    private Long id;
    private String title;
    private String author;
    private BookStatus bookStatus;
    private BookOrigin bookOrigin;
    private Integer likesCount;
    private String coverUrl;
    private String ISBN;
    private String sku;
    private String publisher;
    private Date publishedDate;
    private String edition;
    private Integer pages;
    private Integer stock;
    private Category[] category;
}
