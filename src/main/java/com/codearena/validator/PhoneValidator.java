package com.codearena.validator;

import com.codearena.constants.ValidationConstants;
import com.codearena.interfaces.Validatable;
import com.codearena.util.StringUtils;
import com.codearena.util.ValidationUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Validates mobile numbers using a future-database-friendly normalized value.
 */

public class PhoneValidator implements Validatable<String>{

    @Override
    public List<String> validate(String value) {
        List<String> errors = new ArrayList<>();

        if (StringUtils.isBlank(value)) {
            errors.add("Mobile number is required.");
            return errors;
        }
        if (StringUtils.containsOuterWhitespace(value)) {
            errors.add("Mobile number must not start or end with spaces.");
        }
        if (!ValidationUtils.matches(StringUtils.normalize(value), ValidationConstants.PHONE_PATTERN)) {
            errors.add("Enter a valid mobile number.");
        }

        return errors;
    }


}
