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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class BookClientSearchBooksByBookTitlePactTest {
  private BookClientService bookClientService;

  @Rule
  public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("book_service", this);

  @Pact(consumer="employee_service")
  public RequestResponsePact createGetBookByIsbnPact(PactDslWithProvider builder) {
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json;charset=UTF-8");

    DslPart body = PactDslJsonArray.arrayEachLike()
            .stringType("isbn", "9780132350884")
            .stringType("author", "Robert Cecil Martin")
            .stringType("title", "Clean Code")
            .stringType("publisher", "Prentice Hall")
            .closeObject();
    return builder
            .given("searchBookByBookTitle")
            .uponReceiving("request for book with specific book title")
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
  public void shouldReturnStatusCode200AndAllBooksMatchingTitleWhenSearchBookBy() {
    bookClientService = new BookClientService(mockProvider.getUrl());
    Book[] expected = {new Book("9780132350884", "Robert Cecil Martin",
            "Clean Code", "Prentice Hall")};
    ResponseEntity<Book[]> response = (ResponseEntity<Book[]>) bookClientService.searchBooksBy("Clean");

    assertEquals(200, response.getStatusCode().value());
    assertArrayEquals(expected, response.getBody());
  }
}