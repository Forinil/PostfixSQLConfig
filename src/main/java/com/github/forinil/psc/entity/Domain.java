package com.github.forinil.psc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor(staticName = "of")
public class Domain implements Entity<String> {
    @NotNull
    @NotEmpty
    private final String domain;

    @Override
    public String id() {
        return domain;
    }
}
