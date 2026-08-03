package com.codearena.validator;

import com.codearena.constants.ValidationConstants;
import com.codearena.interfaces.Validatable;
import com.codearena.util.ValidationUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Validates password strength rules for new candidates.
 */
public class PasswordValidator implements Validatable<String> {

    @Override
    public List<String> validate(String value) {
        char[] password = value == null ? null : value.toCharArray();
        try {
            return validate(password);
        } finally {
            if (password != null) {
                Arrays.fill(password, '\0');
            }
        }
    }

    public List<String> validate(char[] value) {
        List<String> errors = new ArrayList<>();

        if (isBlank(value)) {
            errors.add("Password is required.");
            return errors;
        }
        if (value.length < ValidationConstants.MIN_PASSWORD_LENGTH) {
            errors.add("Password must be at least 8 characters.");
        }
        if (!ValidationUtils.hasUppercase(value)) {
            errors.add("Password must include an uppercase letter.");
        }
        if (!ValidationUtils.hasLowercase(value)) {
            errors.add("Password must include a lowercase letter.");
        }
        if (!ValidationUtils.hasDigit(value)) {
            errors.add("Password must include a number.");
        }
        if (!ValidationUtils.hasSpecialCharacter(value)) {
            errors.add("Password must include a special character.");
        }

        return errors;
    }

    private boolean isBlank(char[] value) {
        if (value == null || value.length == 0) {
            return true;
        }
        for (char character : value) {
            if (!Character.isWhitespace(character)) {
                return false;
            }
        }
        return true;
    }
}