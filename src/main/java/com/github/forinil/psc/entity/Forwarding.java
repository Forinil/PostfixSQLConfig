package com.github.forinil.psc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor(staticName = "of")
public class Forwarding implements Entity<String> {
    @NotNull
    @NotEmpty
    private final String source;

    @NotNull
    @NotEmpty
    private final String destination;

    @Override
    public String id() {
        return source;
    }
}
