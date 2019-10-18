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

public class BookClientGetBooksByIsbnWithNoMatchingIsbnPactTest {
  private BookClientService bookClientService;

  @Rule
  public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("book_service", this);

  @Pact(consumer="employee_service")
  public RequestResponsePact createGetBookByIsbnPact(PactDslWithProvider builder) {
    return builder
            .given("getBookByIsbnWithNoMatchingIsbn")
            .uponReceiving("request for book with specific isbn, but isbn in path variable could not be found")
            .path("/books/0")
            .method("GET")
            .willRespondWith()
            .status(404)
            .body("Isbn not found")
            .toPact();
  }

  @Test
  @PactVerification
  public void shouldReturnStatusCode404AndErrorMessageWhenGetBookByWithInvalidIsbn() {
    bookClientService = new BookClientService(mockProvider.getUrl());
    ResponseEntity<?> response =  bookClientService.getBookBy("0");

    assertEquals(404, response.getStatusCode().value());
    assertEquals("Isbn not found", response.getBody());
  }
}