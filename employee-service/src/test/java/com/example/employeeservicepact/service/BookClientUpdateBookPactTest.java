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

public class BookClientUpdateBookPactTest {
  private BookClientService bookClientService;

  @Rule
  public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("book_service", "localhost",
          8082, this);

  @Pact(consumer="employee_service")
  public RequestResponsePact createUpdateBookPact(PactDslWithProvider builder) {
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json;charset=UTF-8");

    DslPart body = new PactDslJsonBody()
            .stringType("isbn", "9780132350883")
            .stringType("author", "Robert Cecil Martin")
            .stringType("title", "Clean Code")
            .stringType("publisher", "Prentice Hall PTR Upper Saddle River, NJ");

    return builder
            .given("updateBook")
            .uponReceiving("request to update a new book")
            .path("/books/123456789")
            .method("PUT")
            .willRespondWith()
            .headers(headers)
            .status(200)
            .body(body)
            .toPact();
  }

  @Test
  @PactVerification
  public void should_return_http_status_200_and_updated_book_when_update_book() {
    bookClientService = new BookClientService(mockProvider.getUrl());
    Book expected = new Book("9780132350883", "Robert Cecil Martin",
            "Clean Code", "Prentice Hall PTR Upper Saddle River, NJ");
    ResponseEntity<Book> response = (ResponseEntity<Book>) bookClientService.updateBook("123456789", expected);

    assertEquals(200, response.getStatusCode().value());
    assertEquals(expected, response.getBody());
  }
}