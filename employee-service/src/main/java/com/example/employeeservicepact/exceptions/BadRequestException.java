package com.example.employeeservicepact.exceptions;

public class BadRequestException extends Exception {
  private int statusCode;

  public BadRequestException(String message, int statusCode) {
    super(message);
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
