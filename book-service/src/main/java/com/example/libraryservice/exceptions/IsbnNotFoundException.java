package com.example.libraryservice.exceptions;

public class IsbnNotFoundException extends Exception {
  public IsbnNotFoundException() {
    super("Isbn not found");
  }
}
