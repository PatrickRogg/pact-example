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

public class BookClientDeleteBookPactTest {
  private BookClientService bookClientService;

  @Rule
  public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("book_service", "localhost",
          8082, this);

  @Pact(consumer="employee_service")
  public RequestResponsePact createUpdateBookPact(PactDslWithProvider builder) {
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json;charset=UTF-8");

    return builder
            .given("deleteBook")
            .uponReceiving("request to delete a book")
            .path("/books/123456789")
            .method("DELETE")
            .willRespondWith()
            .headers(headers)
            .status(200)
            .toPact();
  }

  @Test
  @PactVerification
  public void should_return_http_status_200_when_delete_book() {
    bookClientService = new BookClientService(mockProvider.getUrl());
    ResponseEntity<?> response = bookClientService.deleteBook("123456789");

    assertEquals(200, response.getStatusCode().value());
  }
}