package com.example.libraryservice.controller;

import com.example.libraryservice.entity.Book;
import com.example.libraryservice.exceptions.IsbnNotFoundException;
import com.example.libraryservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

  private BookService bookService;

  @Autowired
  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping
  public ResponseEntity<?> getAllBooks() {
    List<Book> books = bookService.getAllBooks();
    return new ResponseEntity<>(books, HttpStatus.OK);
  }

  @GetMapping("search")
  public ResponseEntity<?> searchBookBy(@RequestParam String bookTitle) {
    List<Book> filteredBooks = bookService.filterBooksBy(bookTitle);
    return new ResponseEntity<>(filteredBooks, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<?> createBook(@RequestBody Book book) {
    if(book.getIsbn() != null) {
      Book createdBook = bookService.create(book);
      return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>("ISBN cant be null", HttpStatus.BAD_REQUEST);
    }
  }

  @PutMapping("{isbn}")
  public ResponseEntity<?> updateBook(@PathVariable String isbn, @RequestBody Book book) {
    try {
      Book updatedBook = bookService.update(isbn, book);
      return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    } catch (IsbnNotFoundException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
  }

  @DeleteMapping
  public ResponseEntity<?> deleteBook(@PathVariable String isbn) {
    try {
      bookService.deleteBookBy(isbn);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (IsbnNotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
