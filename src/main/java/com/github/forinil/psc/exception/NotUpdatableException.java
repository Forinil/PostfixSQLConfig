package com.github.forinil.psc.exception;

public class NotUpdatableException extends Exception {
    public NotUpdatableException() {
        super();
    }

    public NotUpdatableException(String message) {
        super(message);
    }

    public NotUpdatableException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotUpdatableException(Throwable cause) {
        super(cause);
    }

    protected NotUpdatableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
