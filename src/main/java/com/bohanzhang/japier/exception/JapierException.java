package com.bohanzhang.japier.exception;

public class JapierException extends RuntimeException {

    public JapierException(String message) {
        super(message);
    }

    public JapierException(String message, Throwable cause) {
        super(message, cause);
    }
}
