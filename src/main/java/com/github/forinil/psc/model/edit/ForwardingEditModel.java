package com.github.forinil.psc.model.edit;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data(staticConstructor = "of")
public class ForwardingEditModel {
    @NotNull
    @NotEmpty
    private final String source;

    @NotNull
    @NotEmpty
    private final String destination;
}
