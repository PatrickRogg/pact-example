package com.example.employeeservicepact.service;

import com.example.employeeservicepact.entity.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Service
public class BookClientService {
  private final RestTemplate restTemplate;
  private final String bookServiceUrl;

  public BookClientService(@Value("${book-service.base-url}") String bookServiceUrl) {
    this.restTemplate = new RestTemplate();
    this.bookServiceUrl = bookServiceUrl;
  }

  public ResponseEntity<?> getAllBooks() {
    return restTemplate.getForEntity(bookServiceUrl + "/books", Book[].class);
  }

  public ResponseEntity<?> searchBooksBy(String bookTitle) {
    return restTemplate.getForEntity(bookServiceUrl + "/books/search?bookTitle=" + bookTitle, Book[].class);
  }

  public ResponseEntity<?> getBookBy(String isbn) {
    try {
      return restTemplate.getForEntity(bookServiceUrl + "/books/" + isbn, Book.class);
    } catch (HttpStatusCodeException e) {
      return ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
              .body(e.getResponseBodyAsString());
    }
  }

  public ResponseEntity<?> createBook(Book book) {
    try {
      return restTemplate.postForEntity(bookServiceUrl + "/books", book, Book.class);
    } catch (HttpStatusCodeException e) {
      return ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
              .body(e.getResponseBodyAsString());
    }
  }

  public ResponseEntity<?> updateBook(String isbn, Book book) {
    try {
      restTemplate.put(bookServiceUrl + "/books/" + isbn, book);
      return new ResponseEntity<>(book, HttpStatus.OK);
    } catch (HttpStatusCodeException e) {
      return ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
              .body(e.getResponseBodyAsString());
    }
  }

  public ResponseEntity<?> deleteBookBy(String isbn) {
    try {
      restTemplate.delete(bookServiceUrl + "/books/" + isbn);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (HttpStatusCodeException e) {
      return ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
              .body(e.getResponseBodyAsString());
    }
  }
}
