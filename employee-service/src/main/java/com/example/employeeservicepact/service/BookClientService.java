package com.example.employeeservicepact.service;

import com.example.employeeservicepact.entity.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BookClientService {
  private final RestTemplate restTemplate;
  private final String bookServiceUrl;

  public BookClientService(@Value("${book-service.base-url}") String bookServiceUrl) {
    this.restTemplate = new RestTemplate();
    this.bookServiceUrl = bookServiceUrl;
  }

  public ResponseEntity<Book[]> getAllBooks() {
    return restTemplate.getForEntity(bookServiceUrl + "/books", Book[].class);
  }
}
