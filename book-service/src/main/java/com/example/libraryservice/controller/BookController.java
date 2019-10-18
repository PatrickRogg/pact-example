package com.example.libraryservice.controller;

import com.example.libraryservice.entity.Book;
import com.example.libraryservice.exceptions.IsbnNotFoundException;
import com.example.libraryservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BookController {

  private BookService bookService;

  @Autowired
  public BookController(final BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping
  public ResponseEntity<?> getAllBooks() {
    List<Book> books = bookService.getAllBooks();
    return ResponseEntity.status(200).body(books);
  }

  @GetMapping("{isbn}")
  public ResponseEntity<?> getBookByIsbn(@PathVariable String isbn) {
    try {
      Book book = bookService.getBookByIsbn(isbn);
      return ResponseEntity.status(200).body(book);
    } catch (IsbnNotFoundException e) {
      return ResponseEntity.status(404).body(e.getMessage());
    }
  }

  @GetMapping("search")
  public ResponseEntity<?> searchBooksBy(@RequestParam String bookTitle) {
    List<Book> filteredBooks = bookService.filterBooksBy(bookTitle);
    return ResponseEntity.status(200).body(filteredBooks);
  }

  @PostMapping
  public ResponseEntity<?> createBook(@RequestBody @Valid Book book) {
    if(book.getIsbn() != null) {
      Book createdBook = bookService.create(book);
      return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON_UTF8).body(createdBook);
    } else {
      return ResponseEntity.status(400).body("Isbn can not be null");
    }
  }

  @PutMapping("{isbn}")
  public ResponseEntity<?> updateBook(@PathVariable String isbn, @RequestBody @Valid Book book) {
    if(book.getIsbn() == null) {
      return ResponseEntity.status(400).body("Isbn can not be null");
    }

    try {
      bookService.update(isbn, book);
      return ResponseEntity.ok().build();
    } catch (IsbnNotFoundException e) {
      return ResponseEntity.status(404).body(e.getMessage());
    }
  }

  @DeleteMapping("{isbn}")
  public ResponseEntity<?> deleteBook(@PathVariable String isbn) {
    try {
      bookService.deleteBookBy(isbn);
      return ResponseEntity.ok().build();
    } catch (IsbnNotFoundException e) {
      return ResponseEntity.status(404).body(e.getMessage());
    }
  }
}
