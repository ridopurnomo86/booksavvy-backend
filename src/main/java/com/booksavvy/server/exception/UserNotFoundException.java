package com.booksavvy.server.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String identifier) {
        super("User not found: " + identifier);
    }
}