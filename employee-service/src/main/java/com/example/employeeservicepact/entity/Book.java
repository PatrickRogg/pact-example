package com.example.employeeservicepact.entity;


import java.util.Objects;

public class Book {
  private String isbn;
  private String author;
  private String title;
  private String publisher;
  private int priceInCents;

  public Book(String isbn, String author, String title, String publisher, int priceInCents) {
    this.isbn = isbn;
    this.author = author;
    this.title = title;
    this.publisher = publisher;
    this.priceInCents = priceInCents;
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

  public int getPriceInCents() {
    return priceInCents;
  }

  public void setPriceInCents(int priceInCents) {
    this.priceInCents = priceInCents;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Book book = (Book) o;
    return priceInCents == book.priceInCents &&
            isbn.equals(book.isbn) &&
            author.equals(book.author) &&
            title.equals(book.title) &&
            publisher.equals(book.publisher);
  }

  @Override
  public int hashCode() {
    return Objects.hash(isbn, author, title, publisher, priceInCents);
  }
}