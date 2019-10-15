package com.example.employeeservicepact.exceptions;

public class IsbnNotFoundException extends Exception {
  private int statusCode;
  public IsbnNotFoundException(String message, int statusCode) {
    super(message);
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
