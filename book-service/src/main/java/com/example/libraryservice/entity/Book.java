package com.example.libraryservice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Book {
  @Id
  private String isbn;
  private String author;
  private String title;
  private String publisher;

  public Book(String isbn, String author, String title, String publisher) {
    this.isbn = isbn;
    this.author = author;
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

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
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
}