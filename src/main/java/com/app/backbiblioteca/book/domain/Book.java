package com.app.backbiblioteca.book.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;
    @Enumerated(EnumType.STRING)
    private BookOrigin bookOrigin;
    private Integer likesCount;
    private String coverUrl;
    @Column(name = "isbn")
    private String ISBN;
    private String sku;
    private String publisher;
    private Date publishedDate;
    private String edition;
    private Integer pages;
    private Integer stock;
    @Convert(converter = CategorySetConverter.class)
    private Set<Category> category;
    private Boolean active;
}
