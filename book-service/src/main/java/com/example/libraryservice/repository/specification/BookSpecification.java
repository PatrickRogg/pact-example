package com.example.libraryservice.repository.specification;

import com.example.libraryservice.entity.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {
  public static Specification<Book> hasIsbn(String isbn) {
    return (root, query, builder) -> builder.like(root.get("isbn"), isbn);
  }

  public static Specification<Book> hasAuthor(String author) {
    return (root, query, builder) -> builder.like(root.get("author"), author);
  }

  public static Specification<Book> hasTitle(String title) {
    return (root, query, builder) -> builder.like(root.get("title"), title);
  }

  public static Specification<Book> hashPublisher(String publisher) {
    return (root, query, builder) -> builder.like(root.get("publisher"), publisher);
  }
}
