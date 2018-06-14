package com.github.forinil.psc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor(staticName = "of")
public class Forwarding implements Entity<String> {
    @NotNull
    private final String source;

    @NotNull
    private final String destination;

    @Override
    public String id() {
        return source;
    }
}
