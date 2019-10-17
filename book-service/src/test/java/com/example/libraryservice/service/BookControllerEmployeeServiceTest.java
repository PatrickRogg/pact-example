package com.example.libraryservice.service;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.RestPactRunner;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.target.MockMvcTarget;
import com.example.libraryservice.controller.BookController;
import com.example.libraryservice.entity.Book;
import com.example.libraryservice.exceptions.IsbnNotFoundException;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(RestPactRunner.class)
@Provider("book_service")
@PactFolder("../pacts")
public class BookControllerEmployeeServiceTest {

  @Mock
  private BookService bookService;

  @TestTarget
  public final MockMvcTarget target = new MockMvcTarget();

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    BookController bookController = new BookController(bookService);
    target.setControllers(bookController);
  }

  @State("getAllBooks")
  public void getAllBooks() {
    // arrange
    List<Book> books = new ArrayList<>();
    books.add(new Book("9780132350884", "Robert Cecil Martin",
            "Clean Code", "Prentice Hall"));
    when(bookService.getAllBooks()).thenReturn(books);
  }

  @State("getBookByIsbn")
  public void getBookByIsbn() throws IsbnNotFoundException {
    Book book = new Book("9780132350884", "Robert Cecil Martin", "Clean Code", "Prentice Hall");
    when(bookService.getBookByIsbn("9780132350884")).thenReturn(book);
  }

  @State("getBookByIsbnWithNoMatchingIsbn")
  public void getBookByIsbnWithNoMatchingIsbn() throws IsbnNotFoundException {
    when(bookService.getBookByIsbn(eq("0"))).thenThrow(new IsbnNotFoundException());
  }

  @State("searchBookByBookTitle")
  public void searchBookByBookTitle() {
    Book book = new Book("9780132350884", "Robert Cecil Martin", "Clean Code", "Prentice Hall");
    List<Book> books = new ArrayList<>();
    books.add(book);
    when(bookService.filterBooksBy("Clean")).thenReturn(books);
  }

  @State("createBook")
  public void createBook() {
    Book book = new Book("9780132350884", "Robert Cecil Martin", "Clean Code", "Prentice Hall");
    when(bookService.create(any())).thenReturn(book);
  }

  @State("createBookWithNoIsbn")
  public void createBookWithNoIsbn() {
  }

  @State("updateBook")
  public void updateBook() {
    doNothing().when(bookService);
  }

  @State("updateBookWithNoIsbn")
  public void updateBookWithNoIsbn() {
  }

  @State("updateBookWithNoMatchingIsbn")
  public void updateBookWithNoMatchingIsbn() throws IsbnNotFoundException {
    doThrow(new IsbnNotFoundException()).when(bookService).update(eq("0"), any());
  }

  @State("deleteBook")
  public void deleteBook() {
    doNothing().when(bookService);
  }

  @State("deleteBookWithNoMatchingIsbn")
  public void deleteBookWithNoMatchingIsbn() throws IsbnNotFoundException {
    doThrow(new IsbnNotFoundException()).when(bookService).deleteBookBy(eq("0"));
  }
}