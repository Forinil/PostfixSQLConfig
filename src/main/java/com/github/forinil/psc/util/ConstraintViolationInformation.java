package com.github.forinil.psc.util;

import lombok.Builder;
import lombok.Getter;

@Builder(builderClassName = "Builder")
public class ConstraintViolationInformation {
    @Getter
    private final String message;

    private final Object object;

    @SuppressWarnings("unchecked")
    public <T> T getObject() {
        return (T) object;
    }
}
