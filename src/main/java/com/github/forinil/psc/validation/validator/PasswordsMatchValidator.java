package com.github.forinil.psc.validation.validator;

import com.github.forinil.psc.validation.constraint.ValidatePasswordsMatch;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class PasswordsMatchValidator implements ConstraintValidator<ValidatePasswordsMatch, Object> {
    private String newPasswordFieldName;
    private String confirmNewPasswordFieldName;

    @Override
    public void initialize(ValidatePasswordsMatch constraintAnnotation) {
        this.newPasswordFieldName = constraintAnnotation.newPasswordFieldName();
        this.confirmNewPasswordFieldName = constraintAnnotation.confirmNewPasswordFieldName();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            val clazz = value.getClass();
            val newPasswordField = clazz.getDeclaredField(newPasswordFieldName);
            val confirmNewPasswordField = clazz.getDeclaredField(confirmNewPasswordFieldName);

            newPasswordField.setAccessible(true);
            confirmNewPasswordField.setAccessible(true);

            val newPassword = newPasswordField.get(value);
            val confirmNewPassword = confirmNewPasswordField.get(value);

            return newPassword != null && newPassword.equals(confirmNewPassword);
        } catch (NoSuchFieldException e) {
            logger.error("One of the password fields is missing", e);
            return false;
        } catch (IllegalAccessException e) {
            logger.error("One of the password fields is inaccessible", e);
            return false;
        }
    }
}
