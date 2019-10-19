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
import com.example.libraryservice.repository.BookRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(RestPactRunner.class)
@Provider("book_service")
@PactFolder("../pacts")
public class BookControllerEmployeeServiceTest {

  @InjectMocks
  private BookService bookService;

  @Mock
  private BookRepository bookRepository;

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
    when(bookRepository.findAll()).thenReturn(books);
  }

  @State("getBookByIsbn")
  public void getBookByIsbn() throws IsbnNotFoundException {
    Book book = new Book("9780132350884", "Robert Cecil Martin", "Clean Code", "Prentice Hall");
    when(bookRepository.findById("9780132350884")).thenReturn(Optional.of(book));
  }

  @State("getBookByIsbnWithNoMatchingIsbn")
  public void getBookByIsbnWithNoMatchingIsbn() throws IsbnNotFoundException {
    when(bookRepository.findById(anyString())).thenReturn(Optional.empty());
  }

  @State("searchBookByBookTitle")
  public void searchBookByBookTitle() {
    Book book = new Book("9780132350884", "Robert Cecil Martin", "Clean Code", "Prentice Hall");
    List<Book> books = new ArrayList<>();
    books.add(book);
    when(bookRepository.findByTitleAndAuthor("Clean", "Robert Cecil Martin")).thenReturn(books);
  }

  @State("createBook")
  public void createBook() {
    when(bookRepository.save(any())).then(i -> (Book) i.getArgument(0));
  }

  @State("createBookWithNoIsbn")
  public void createBookWithNoIsbn() {
    when(bookRepository.save(any())).then(i -> (Book) i.getArgument(0));
  }

  @State("updateBook")
  public void updateBook() {
    Book book = new Book("123456789", "Robert Cecil Martin", "Clean Code", "Prentice Hall");
    when(bookRepository.findById(anyString())).thenReturn(Optional.of(book));
    when(bookRepository.save(any())).then(i -> (Book) i.getArgument(0));
  }

  @State("updateBookWithNoIsbn")
  public void updateBookWithNoIsbn() {
    Book book = new Book("9780132350884", "Robert Cecil Martin", "Clean Code", "Prentice Hall");
    when(bookRepository.save(any())).then(i -> (Book) i.getArgument(0));
  }

  @State("updateBookWithNoMatchingIsbn")
  public void updateBookWithNoMatchingIsbn() throws IsbnNotFoundException {
    Book oldBook = new Book("123456789", "Robert Cecil Martin", "Clean Code", "Prentice Hall");
    when(bookRepository.findById("123456789")).thenReturn(Optional.of(oldBook));
    when(bookRepository.save(any())).then(i -> (Book) i.getArgument(0));
  }

  @State("deleteBookBy")
  public void deleteBookBy() {
    when(bookRepository.findById(anyString())).thenReturn(Optional.of(new Book()));
  }

  @State("deleteBookWithNoMatchingIsbn")
  public void deleteBookWithNoMatchingIsbn() {
  }
}