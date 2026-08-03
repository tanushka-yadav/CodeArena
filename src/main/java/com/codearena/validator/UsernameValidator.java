package com.codearena.validator;

import com.codearena.constants.ValidationConstants;
import com.codearena.interfaces.Validatable;
import com.codearena.util.StringUtils;
import com.codearena.util.ValidationUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Validates usernames before duplicate checks are performed.
 */

public class UsernameValidator implements Validatable<String>{

    @Override
    public List<String> validate(String value) {
        List<String> errors = new ArrayList<>();

        if (StringUtils.isBlank(value)) {
            errors.add("Username is required.");
            return errors;
        }
        if (StringUtils.containsOuterWhitespace(value)) {
            errors.add("Username must not start or end with spaces.");
        }
        if (!ValidationUtils.matches(StringUtils.normalize(value), ValidationConstants.USERNAME_PATTERN)) {
            errors.add("Username must start with a letter and use 4-20 letters, numbers, or underscores.");
        }

        return errors;
    }


}
