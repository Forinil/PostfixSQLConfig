package com.github.forinil.psc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor(staticName = "of")
public class User {
    @NotNull
    @Email
    private final String email;

    @NotNull
    private final String password;
}
