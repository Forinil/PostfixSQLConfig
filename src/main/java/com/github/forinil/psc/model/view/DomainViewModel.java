package com.github.forinil.psc.model.view;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data(staticConstructor = "of")
public class DomainViewModel {
    @NotNull
    @NotEmpty
    private final String domain;
}
