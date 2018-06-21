package com.github.forinil.psc.model.create;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data(staticConstructor = "of")
public class TransportCreateModel {
    @NotNull
    @NotEmpty
    private final String domain;

    @NotNull
    @NotEmpty
    private final String transport;
}
