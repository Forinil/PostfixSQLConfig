package com.github.forinil.psc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor(staticName = "of")
public class Transport implements Entity<String> {
    @NotNull
    @NotEmpty
    private final String domain;

    @NotNull
    @NotEmpty
    private final String transport;

    @Override
    public String id() {
        return domain;
    }
}
