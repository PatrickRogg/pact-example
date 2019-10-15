package com.example.employeeservicepact.service;


import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
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
import static org.junit.Assert.assertTrue;

public class BookClientCreateBookPactTest {
  private BookClientService bookClientService;

  @Rule
  public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("book_service", "localhost",
          8082, this);

  @Pact(consumer="employee_service")
  public RequestResponsePact createCreateBookPact(PactDslWithProvider builder) {
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json;charset=UTF-8");

    DslPart body = new PactDslJsonBody()
            .stringType("isbn", "9780132350884")
            .stringType("author", "Robert Cecil Martin")
            .stringType("title", "Clean Code")
            .stringType("publisher", "Prentice Hall PTR Upper Saddle River, NJ");

    return builder
            .given("createBook")
            .uponReceiving("request to create a new book")
            .path("/books")
            .method("POST")
            .willRespondWith()
            .headers(headers)
            .status(201)
            .body(body)
            .toPact();
  }

  @Test
  @PactVerification
  public void should_return_http_status_201_and_created_book_when_create_book() {
    bookClientService = new BookClientService(mockProvider.getUrl());
    Book expected = new Book("9780132350884", "Robert Cecil Martin",
            "Clean Code", "Prentice Hall PTR Upper Saddle River, NJ");
    ResponseEntity<Book> response = (ResponseEntity<Book>) bookClientService.createBook(expected);

    assertEquals(201, response.getStatusCode().value());
    assertEquals(expected, response.getBody());
  }
}