package com.codearena.constants;

/**
 * Central validation rules used by the registration module.
 */

public final class ValidationConstants {

    public static final int MIN_NAME_LENGTH = 3;
    public static final int MIN_USERNAME_LENGTH = 4;
    public static final int MAX_USERNAME_LENGTH = 20;
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MIN_CANDIDATE_AGE = 16;

    public static final String NAME_PATTERN = "^[A-Za-z][A-Za-z .'-]{2,}$";
    public static final String USERNAME_PATTERN = "^[A-Za-z][A-Za-z0-9_]{3,19}$";
    public static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    public static final String PHONE_PATTERN = "^[6-9][0-9]{9}$|^\\+?[1-9][0-9]{9,14}$";

    private ValidationConstants() {
    }

}
