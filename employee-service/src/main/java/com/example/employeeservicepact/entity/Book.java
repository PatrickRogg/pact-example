package com.example.employeeservicepact.entity;


public class Book {
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

  @Override
  public String toString() {
    return  "isbn: " + isbn + " author: " + author + " title:" + title + "publisher: " + publisher;
  }
}