package com.example.libraryservice.service;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.target.MockMvcTarget;
import com.example.libraryservice.BookServiceApplication;
import com.example.libraryservice.controller.BookController;
import com.example.libraryservice.entity.Book;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.SpringApplication;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(PactRunner.class)
@Provider("book_service")
@PactFolder("../pacts")
public class BookServiceTest {

  @InjectMocks
  private BookController bookController;

  @Mock
  private BookService bookService;

  @TestTarget
  public final MockMvcTarget target = new MockMvcTarget();

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    bookController = new BookController(bookService);
    target.setControllers(bookController);
  }

  @State("getAllBooks")
  public void getAllBooks() {
    List<Book> books = new ArrayList<>();
    books.add(new Book("9780132350884", "Robert Cecil Martin", "Clean Code", "Prentice Hall PTR Upper Saddle River, NJ"));
    books.add(new Book("9788131722428", "Andy Hunt", "The Pragmatic Programmer", "Addison Wesley"));
    when(bookService.getAllBooks()).thenReturn(books);
  }

  @Test
  public void create() {
  }

  @Test
  public void update() {
  }

  @Test
  public void deleteBookBy() {
  }

  @Test
  public void filterBooksBy() {
  }
}