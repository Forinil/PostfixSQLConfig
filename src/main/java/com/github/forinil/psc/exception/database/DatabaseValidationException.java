package com.github.forinil.psc.exception.database;

import com.github.forinil.psc.util.ConstraintViolationInformation;
import lombok.Getter;

import java.util.Set;

public class DatabaseValidationException extends DatabaseException {
    @Getter
    private Set<ConstraintViolationInformation> constraintViolationInformation;

    public DatabaseValidationException(Set<ConstraintViolationInformation> constraintViolationInformation) {
        super();
        this.constraintViolationInformation = constraintViolationInformation;
    }

    public DatabaseValidationException(String message, Set<ConstraintViolationInformation> constraintViolationInformation) {
        super(message);
        this.constraintViolationInformation = constraintViolationInformation;
    }

    public DatabaseValidationException(String message, Throwable cause, Set<ConstraintViolationInformation> constraintViolationInformation) {
        super(message, cause);
        this.constraintViolationInformation = constraintViolationInformation;
    }

    public DatabaseValidationException(Throwable cause, Set<ConstraintViolationInformation> constraintViolationInformation) {
        super(cause);
        this.constraintViolationInformation = constraintViolationInformation;
    }

    protected DatabaseValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Set<ConstraintViolationInformation> constraintViolationInformation) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.constraintViolationInformation = constraintViolationInformation;
    }
}
