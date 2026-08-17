package com.salessavvy.backend.exception;

public class ProductNotFoundException extends RuntimeException {
   public ProductNotFoundException(String message) {
    super(message); // super(message) is what passes your message into storage mechanism.
   }
}