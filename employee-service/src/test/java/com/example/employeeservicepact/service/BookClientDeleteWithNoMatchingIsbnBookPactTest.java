package com.example.employeeservicepact.service;


import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;

public class BookClientDeleteWithNoMatchingIsbnBookPactTest {
  private BookClientService bookClientService;

  @Rule
  public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("book_service", this);

  @Pact(consumer="employee_service")
  public RequestResponsePact createUpdateBookPact(PactDslWithProvider builder) {
    return builder
            .given("deleteBookWithNoMatchingIsbn")
            .uponReceiving("request to delete a book, but isbn in path variable could not be found")
            .path("/books/0")
            .method("DELETE")
            .willRespondWith()
            .status(404)
            .body("Isbn not found")
            .toPact();
  }

  @Test
  @PactVerification
  public void shouldReturnStatusCode404AndErrorMessageWhenDeleteBookByWithInvalidIsbn() {
    bookClientService = new BookClientService(mockProvider.getUrl());
    ResponseEntity<?> response = bookClientService.deleteBookBy("0");

    assertEquals(404, response.getStatusCode().value());
    assertEquals("Isbn not found", response.getBody());
  }
}