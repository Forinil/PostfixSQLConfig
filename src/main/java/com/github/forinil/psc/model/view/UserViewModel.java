package com.github.forinil.psc.model.view;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.NotEmpty;

@Data(staticConstructor = "of")
public class UserViewModel {

    @NonNull
    @NotEmpty
    private final String email;

    @NonNull
    @NotEmpty
    private final String password;
}
