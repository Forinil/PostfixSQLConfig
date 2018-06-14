package com.github.forinil.psc.exception.database;

public class ActionNotSupportedException extends DatabaseException {
    public ActionNotSupportedException() {
        super();
    }

    public ActionNotSupportedException(String message) {
        super(message);
    }

    public ActionNotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActionNotSupportedException(Throwable cause) {
        super(cause);
    }

    protected ActionNotSupportedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
