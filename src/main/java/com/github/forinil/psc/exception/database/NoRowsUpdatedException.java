package com.github.forinil.psc.exception.database;

public class NoRowsUpdatedException extends DatabaseException {
    public NoRowsUpdatedException() {
        super();
    }

    public NoRowsUpdatedException(String message) {
        super(message);
    }

    public NoRowsUpdatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoRowsUpdatedException(Throwable cause) {
        super(cause);
    }

    protected NoRowsUpdatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
