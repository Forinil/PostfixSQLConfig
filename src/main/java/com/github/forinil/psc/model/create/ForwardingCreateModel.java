package com.github.forinil.psc.model.create;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data(staticConstructor = "of")
public class ForwardingCreateModel {
    @NotNull
    @NotEmpty
    private final String source;

    @NotNull
    @NotEmpty
    private final String destination;
}
