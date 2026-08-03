package com.codearena.validator;

import com.codearena.constants.ValidationConstants;
import com.codearena.interfaces.Validatable;
import com.codearena.util.StringUtils;
import com.codearena.util.ValidationUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Validates email address syntax.
 */

public class EmailValidator implements Validatable<String>{

    @Override
    public List<String> validate(String value) {
        List<String> errors = new ArrayList<>();

        if (StringUtils.isBlank(value)) {
            errors.add("Email address is required.");
            return errors;
        }
        if (StringUtils.containsOuterWhitespace(value)) {
            errors.add("Email address must not start or end with spaces.");
        }
        if (!ValidationUtils.matches(StringUtils.normalize(value), ValidationConstants.EMAIL_PATTERN)) {
            errors.add("Enter a valid email address.");
        }

        return errors;
    }


}
