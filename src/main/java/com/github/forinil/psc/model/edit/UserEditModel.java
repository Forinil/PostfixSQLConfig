package com.github.forinil.psc.model.edit;

import com.github.forinil.psc.validation.constraint.ValidatePasswordsMatch;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data(staticConstructor = "of")
@ValidatePasswordsMatch
public class UserEditModel {
    @NotNull
    private final String email;

    @NotNull
    private final String password;

    @NotNull
    private final String newPassword;

    @NotNull
    private final String confirmNewPassword;
}
