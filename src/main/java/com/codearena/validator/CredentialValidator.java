package com.codearena.validator;

import com.codearena.constants.ValidationConstants;
import com.codearena.dto.LoginRequest;
import com.codearena.interfaces.Validatable;
import com.codearena.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Validates login credentials before authentication is attempted.
 */
public class CredentialValidator implements Validatable<LoginRequest> {

    private final EmailValidator emailValidator;
    private final UsernameValidator usernameValidator;
    private final PasswordValidator passwordValidator;

    public CredentialValidator() {
        this(new EmailValidator(), new UsernameValidator(), new PasswordValidator());
    }

    public CredentialValidator(EmailValidator emailValidator, UsernameValidator usernameValidator,
                               PasswordValidator passwordValidator) {
        this.emailValidator = emailValidator;
        this.usernameValidator = usernameValidator;
        this.passwordValidator = passwordValidator;
    }

    @Override
    public List<String> validate(LoginRequest request) {
        List<String> errors = new ArrayList<>();

        if (request == null) {
            errors.add("Login credentials are required.");
            return errors;
        }

        errors.addAll(validateUsernameOrEmail(request.getUsernameOrEmail()));
        char[] password = request.getPassword();
        try {
            errors.addAll(passwordValidator.validate(password));
        } finally {
            Arrays.fill(password, '\0');
        }

        return errors;
    }

    private List<String> validateUsernameOrEmail(String usernameOrEmail) {
        List<String> errors = new ArrayList<>();

        if (StringUtils.isBlank(usernameOrEmail)) {
            errors.add("Username or email is required.");
            return errors;
        }
        if (StringUtils.containsOuterWhitespace(usernameOrEmail)) {
            errors.add("Username or email must not start or end with spaces.");
        }
        if (StringUtils.normalize(usernameOrEmail).length() > ValidationConstants.MAX_LOGIN_IDENTIFIER_LENGTH) {
            errors.add("Username or email must be 100 characters or fewer.");
        }

        if (usernameOrEmail.contains("@")) {
            errors.addAll(emailValidator.validate(usernameOrEmail));
        } else {
            errors.addAll(usernameValidator.validate(usernameOrEmail));
        }

        return errors;
    }
}
