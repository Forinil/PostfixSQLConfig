package com.github.forinil.psc.model.view;

import lombok.Data;
import lombok.NonNull;

@Data(staticConstructor = "of")
public class UserViewModel {

    @NonNull
    private final String email;

    @NonNull
    private final String password;
}
