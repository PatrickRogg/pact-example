package com.example.employeeservicepact.service;


import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import com.example.employeeservicepact.entity.Book;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BookClientSearchBooksByBookTitlePactTest {
  private BookClientService bookClientService;

  @Rule
  public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("book_service", "localhost",
          8082, this);

  @Pact(consumer="employee_service")
  public RequestResponsePact createGetBookByIsbnPact(PactDslWithProvider builder) {
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json;charset=UTF-8");

    DslPart body = PactDslJsonArray.arrayEachLike()
            .stringType("isbn", "9780132350884")
            .stringType("author", "Robert Cecil Martin")
            .stringType("title", "Clean Code")
            .stringType("publisher", "Prentice Hall PTR Upper Saddle River, NJ")
            .closeObject();
    return builder
            .given("searchBookByBookTitle")
            .uponReceiving("request for book with specific")
            .path("/books/search")
            .query("bookTitle=Clean")
            .method("GET")
            .willRespondWith()
            .headers(headers)
            .status(200)
            .body(body)
            .toPact();
  }

  @Test
  @PactVerification
  public void should_have_http_status_200_when_get_book_by_book_titles() {
    bookClientService = new BookClientService(mockProvider.getUrl());
    ResponseEntity<Book[]> response = (ResponseEntity<Book[]>) bookClientService.searchBooksBy("Clean");

    assertEquals(200, response.getStatusCode().value());
  }

  @Test
  @PactVerification
  public void should_have_books_with_search_term_when_get_book_by_book_title() {
    bookClientService = new BookClientService(mockProvider.getUrl());
    ResponseEntity<Book[]> response = (ResponseEntity<Book[]>) bookClientService.searchBooksBy("Clean");

    assertTrue(response.getBody()[0].getTitle().contains("Clean"));
  }
}