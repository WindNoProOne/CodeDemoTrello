package com.backend.api.Exception;

public class InvalidCardStateException  extends RuntimeException {
    public InvalidCardStateException(String message) {
        super(message);
    }
}