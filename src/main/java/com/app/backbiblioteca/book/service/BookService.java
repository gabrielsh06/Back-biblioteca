package com.app.backbiblioteca.book.service;

import com.app.backbiblioteca.book.domain.Book;
import com.app.backbiblioteca.book.domain.Category;
import com.app.backbiblioteca.book.dto.request.BookCreateRequest;
import com.app.backbiblioteca.book.dto.request.BookUpdateRequest;
import com.app.backbiblioteca.book.dto.response.BookResponse;
import com.app.backbiblioteca.book.repository.BookRepository;
import com.app.backbiblioteca.common.dto.PageResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookResponse getBook(Long id) {
        return bookRepository.findById(id)
                .filter(Book::getActive)
                .map(BookResponse::new)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .filter(Book::getActive)
                .map(BookResponse::new)
                .toList();
    }

    public PageResponse<BookResponse> getBooksPaged(String search, Category category, Pageable pageable) {
        String categoryName = (category != null) ? category.name() : null;
        String searchTerm = (search != null && !search.isBlank()) ? search.trim() : null;

        Page<Book> booksPage = bookRepository.searchBooks(searchTerm, categoryName, pageable);
        return PageResponse.from(booksPage.map(BookResponse::new));
    }

    public BookResponse registerBook(BookCreateRequest bookRequest) {
        Book book = bookRequest.toEntity();
        book.setActive(true);

        return new BookResponse(bookRepository.save(book));
    }

    public BookResponse updateBook(Long id, BookUpdateRequest bookRequest) {
        Book book = bookRepository.findById(id)
                .filter(Book::getActive)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        book.setTitle(bookRequest.title());
        book.setAuthor(bookRequest.author());
        book.setBookStatus(bookRequest.bookStatus());
        book.setBookOrigin(bookRequest.bookOrigin());
        book.setCoverUrl(bookRequest.coverUrl());
        book.setISBN(bookRequest.ISBN());
        book.setSku(bookRequest.sku());
        book.setPublisher(bookRequest.publisher());
        book.setPublishedDate(bookRequest.publishedDate());
        book.setEdition(bookRequest.edition());
        book.setPages(bookRequest.pages());
        book.setStock(bookRequest.stock());
        book.setCategory(bookRequest.category());

        return new BookResponse(bookRepository.save(book));
    }

    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .filter(Book::getActive)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        book.setActive(false);

        bookRepository.save(book);
    }
}
