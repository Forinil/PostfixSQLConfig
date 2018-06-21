package com.github.forinil.psc.model.edit;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data(staticConstructor = "of")
public class TransportEditModel {
    @NotNull
    @NotEmpty
    private final String domain;

    @NotNull
    @NotEmpty
    private final String transport;
}
