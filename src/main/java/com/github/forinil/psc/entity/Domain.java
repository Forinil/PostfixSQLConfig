package com.github.forinil.psc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor(staticName = "of")
public class Domain {
    @NotNull
    private final String domain;
}
