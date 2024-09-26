package com.backend.api.Exception;

public class InvalidUserStateException  extends RuntimeException {
    public InvalidUserStateException(String message) {
        super(message);
    }
}