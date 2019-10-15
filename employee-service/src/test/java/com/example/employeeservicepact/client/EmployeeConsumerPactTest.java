package com.example.employeeservicepact.client;


import au.com.dius.pact.consumer.*;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import com.example.employeeservicepact.entity.Book;
import com.example.employeeservicepact.service.BookClientService;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.ResponseEntity;


import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class EmployeeConsumerPactTest {
  private BookClientService bookClientService;

  @Rule
  public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("ExampleProvider",this);

  @Pact(consumer="JunitRuleConsumer")
  public RequestResponsePact createPact(PactDslWithProvider builder) {
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json;charset=UTF-8");

    return builder
            .uponReceiving("request for all books")
            .path("/books")
            .method("GET")
            .willRespondWith()
            .headers(headers)
            .status(200)
            .body("[" +
                    "{\n" +
                    "    \"isbn\": \"9780132350884\",\n" +
                    "    \"author\": \"Robert Cecil Martin\",\n" +
                    "    \"title\": \"Clean Code\",\n" +
                    "    \"publisher\": \"Prentice Hall PTR Upper Saddle River, NJ\",\n" +
                    "}," +
                    "{\n" +
                    "    \"isbn\": \"9788131722428\",\n" +
                    "    \"author\": \"Andy Hunt\",\n" +
                    "    \"title\": \"The Pragmatic Programmer\",\n" +
                    "    \"publisher\": \"Addison Wesly\",\n" +
                    "}" +
                    "]")
            .toPact();
  }

  @Test
  @PactVerification
  public void runTest() {
    bookClientService = new BookClientService(mockProvider.getUrl());
    ResponseEntity<Book[]> response = bookClientService.getAllBooks();

    assertEquals(response.getStatusCode().value(), 200);
    assertEquals(response.getBody().length, 2);
  }
}