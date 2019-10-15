package com.example.libraryservice.config;

import com.example.libraryservice.entity.Book;
import com.example.libraryservice.service.BookService;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class BookServiceDatabaseInit {
  private final BookService bookService;
  private final Faker faker;

  @Autowired
  public BookServiceDatabaseInit(BookService bookService) {
    this.bookService = bookService;
    this.faker = new Faker();
  }

  @PostConstruct
  public void initDatabase() {
    for(int i = 0; i < 100; i++) {
      Book book = new Book("978386680192" + i, faker.book().author(), faker.book().title(), faker.book().publisher());
      bookService.create(book);
    }
  }
}
