package com.backend.api.Exception;

public class InvalidCommentStateException  extends RuntimeException {
    public InvalidCommentStateException(String message) {
        super(message);
    }
}