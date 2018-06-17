package com.github.forinil.psc.validation.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE, METHOD, PARAMETER, ANNOTATION_TYPE, CONSTRUCTOR })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { })
public @interface ValidatePasswordsMatch {
    String message() default "{com.github.forinil.psc.validation.constraint.ValidatePasswordsMatch.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String newPasswordFieldName() default "newPassword";

    String confirmNewPasswordFieldName() default "confirmNewPassword";
}
