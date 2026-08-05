package com.codearena.exception;

/**
 * Wraps persistence failures with a user-safe message.
 */
public class DatabaseException extends RuntimeException {

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
