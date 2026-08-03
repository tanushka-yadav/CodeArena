package com.codearena.validator;

import com.codearena.constants.ValidationConstants;
import com.codearena.interfaces.Validatable;
import com.codearena.util.StringUtils;
import com.codearena.util.ValidationUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Validates candidate full names.
 */

public class NameValidator implements Validatable<String>{

    private static final int MAX_NAME_LENGTH = 60;

    @Override
    public List<String> validate(String value) {
        List<String> errors = new ArrayList<>();

        if (StringUtils.isBlank(value)) {
            errors.add("Full name is required.");
            return errors;
        }
        if (StringUtils.containsOuterWhitespace(value)) {
            errors.add("Full name must not start or end with spaces.");
        }
        if (!ValidationUtils.lengthBetween(value, ValidationConstants.MIN_NAME_LENGTH, MAX_NAME_LENGTH)) {
            errors.add("Full name must be between 3 and 60 characters.");
        }
        if (!ValidationUtils.matches(StringUtils.normalize(value), ValidationConstants.NAME_PATTERN)) {
            errors.add("Full name can contain only letters, spaces, apostrophes, periods, and hyphens.");
        }

        return errors;
    }


}
