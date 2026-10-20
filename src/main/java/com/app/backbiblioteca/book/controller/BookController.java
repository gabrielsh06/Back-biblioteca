package com.app.backbiblioteca.book.controller;

import com.app.backbiblioteca.book.domain.Category;
import com.app.backbiblioteca.book.dto.request.BookCreateRequest;
import com.app.backbiblioteca.book.dto.request.BookUpdateRequest;
import com.app.backbiblioteca.book.dto.response.BookLookupResponse;
import com.app.backbiblioteca.book.dto.response.BookResponse;
import com.app.backbiblioteca.book.service.BookService;
import com.app.backbiblioteca.book.service.ExternalBookService;
import com.app.backbiblioteca.common.dto.PageResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final ExternalBookService externalBookService;

    public BookController(BookService bookService, ExternalBookService externalBookService) {
        this.bookService = bookService;
        this.externalBookService = externalBookService;
    }

    @GetMapping("/lookup/{isbn}")
    public ResponseEntity<BookLookupResponse> lookupByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(externalBookService.lookupByIsbn(isbn));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBook(id));
    }

    @GetMapping
    public ResponseEntity<PageResponse<BookResponse>> getBooks(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Category category,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(bookService.getBooksPaged(search, category, pageable));
    }

    @PostMapping
    public ResponseEntity<BookResponse> registerBook(@Valid @RequestBody BookCreateRequest bookRequest) {
        BookResponse createdBook = bookService.registerBook(bookRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id, @Valid @RequestBody BookUpdateRequest bookRequest) {
        return ResponseEntity.ok(bookService.updateBook(id, bookRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
