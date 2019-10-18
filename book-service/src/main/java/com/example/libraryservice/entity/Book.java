package com.example.libraryservice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class Book {
  @Id
  @NotNull
  private String isbn;
  @NotNull
  private String authors;
  @NotNull
  private String title;
  @NotNull
  private String publisher;

  public Book(String isbn, String authors, String title, String publisher) {
    this.isbn = isbn;
    this.authors = authors;
    this.title = title;
    this.publisher = publisher;
  }

  public Book() {
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getAuthors() {
    return authors;
  }

  public void setAuthors(String authors) {
    this.authors = authors;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Book book = (Book) o;
    return isbn.equals(book.isbn) &&
            authors.equals(book.authors) &&
            title.equals(book.title) &&
            publisher.equals(book.publisher);
  }

  @Override
  public int hashCode() {
    return Objects.hash(isbn, authors, title, publisher);
  }
}