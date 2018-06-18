package com.github.forinil.psc.model.create;

import com.github.forinil.psc.validation.constraint.ValidatePasswordsMatch;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data(staticConstructor = "of")
@ValidatePasswordsMatch(newPasswordFieldName = "password", confirmNewPasswordFieldName = "confirmPassword")
public class UserCreateModel {
    @Email
    @NotNull
    @NotEmpty
    private final String email;

    @NotNull
    @NotEmpty
    private final String password;

    @NotNull
    @NotEmpty
    private final String confirmPassword;
}
