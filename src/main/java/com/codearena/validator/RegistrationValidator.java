package com.codearena.validator;

import com.codearena.constants.ValidationConstants;
import com.codearena.dto.RegistrationRequest;
import com.codearena.interfaces.Validatable;
import com.codearena.util.DateTimeUtil;
import com.codearena.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Coordinates all validation rules for candidate registration.
 */
public class RegistrationValidator implements Validatable<RegistrationRequest> {

    private final NameValidator nameValidator;
    private final UsernameValidator usernameValidator;
    private final EmailValidator emailValidator;
    private final PhoneValidator phoneValidator;
    private final PasswordValidator passwordValidator;

    public RegistrationValidator() {
        this(new NameValidator(), new UsernameValidator(), new EmailValidator(),
                new PhoneValidator(), new PasswordValidator());
    }

    public RegistrationValidator(NameValidator nameValidator, UsernameValidator usernameValidator,
                                 EmailValidator emailValidator, PhoneValidator phoneValidator,
                                 PasswordValidator passwordValidator) {
        this.nameValidator = nameValidator;
        this.usernameValidator = usernameValidator;
        this.emailValidator = emailValidator;
        this.phoneValidator = phoneValidator;
        this.passwordValidator = passwordValidator;
    }

    @Override
    public List<String> validate(RegistrationRequest request) {
        List<String> errors = new ArrayList<>();

        if (request == null) {
            errors.add("Registration details are required.");
            return errors;
        }

        char[] password = request.getPassword();
        char[] confirmPassword = request.getConfirmPassword();
        try {
            errors.addAll(nameValidator.validate(request.getFullName()));
            errors.addAll(usernameValidator.validate(request.getUsername()));
            errors.addAll(emailValidator.validate(request.getEmailAddress()));
            errors.addAll(phoneValidator.validate(request.getMobileNumber()));
            errors.addAll(passwordValidator.validate(password));
            errors.addAll(validatePasswordConfirmation(password, confirmPassword));
            errors.addAll(validateGender(request));
            errors.addAll(validateDateOfBirth(request.getDateOfBirthInput(), request.getDateOfBirth()));
        } finally {
            Arrays.fill(password, '\0');
            Arrays.fill(confirmPassword, '\0');
        }

        return errors;
    }

    private List<String> validatePasswordConfirmation(char[] password, char[] confirmPassword) {
        List<String> errors = new ArrayList<>();

        if (isBlank(confirmPassword)) {
            errors.add("Confirm password is required.");
        } else if (!Arrays.equals(password, confirmPassword)) {
            errors.add("Password and confirm password must match.");
        }

        return errors;
    }

    private List<String> validateGender(RegistrationRequest request) {
        List<String> errors = new ArrayList<>();
        if (request.getGender() == null) {
            errors.add("Gender is required.");
        }
        return errors;
    }

    private List<String> validateDateOfBirth(String dateOfBirthInput, LocalDate dateOfBirth) {
        List<String> errors = new ArrayList<>();

        if (dateOfBirth == null) {
            if (!StringUtils.isBlank(dateOfBirthInput)) {
                errors.add("Date of birth must use " + DateTimeUtil.inputPattern() + ".");
                return errors;
            }
            errors.add("Date of birth is required. Use " + DateTimeUtil.inputPattern() + ".");
            return errors;
        }
        if (dateOfBirth.isAfter(LocalDate.now())) {
            errors.add("Date of birth cannot be in the future.");
            return errors;
        }
        if (DateTimeUtil.calculateAge(dateOfBirth) < ValidationConstants.MIN_CANDIDATE_AGE) {
            errors.add("Candidate must be at least 16 years old.");
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