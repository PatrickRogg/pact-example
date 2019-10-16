package com.example.employeeservicepact.service;


import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import com.example.employeeservicepact.entity.Book;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class BookClientUpdateBookWithNoMatchingIsbnPactTest {
  private BookClientService bookClientService;

  @Rule
  public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("book_service", this);

  @Pact(consumer="employee_service")
  public RequestResponsePact createUpdateBookPact(PactDslWithProvider builder) {
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json;charset=UTF-8");

    DslPart request = new PactDslJsonBody()
            .stringValue("isbn", "9780132350883")
            .stringValue("author", "Robert Cecil Martin")
            .stringValue("title", "Clean Code")
            .stringValue("publisher", "Prentice Hall");

    return builder
            .given("updateBookWithNoMatchingIsbn")
            .uponReceiving("request to update a book, but isbn in path variable could not be found")
            .path("/books/0")
            .method("PUT")
            .body(request)
            .headers(headers)
            .willRespondWith()
            .status(404)
            .body("Isbn not found")
            .toPact();
  }

  @Test
  @PactVerification
  public void should_return_http_status_409_and_error_message_when_update_book_was_called_with_() {
    bookClientService = new BookClientService(mockProvider.getUrl());
    Book book = new Book("9780132350883", "Robert Cecil Martin",
            "Clean Code", "Prentice Hall");
    ResponseEntity<?> response = bookClientService.updateBook("0", book);

    assertEquals(404, response.getStatusCode().value());
    assertEquals("Isbn not found", response.getBody());
  }
}