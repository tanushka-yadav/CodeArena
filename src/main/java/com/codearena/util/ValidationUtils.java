package com.codearena.util;

import java.util.regex.Pattern;

/**
 * Common validation helpers for null-safe checks.
 */
public final class ValidationUtils {

    private ValidationUtils() {
    }

    public static boolean matches(String value, String pattern) {
        return value != null && Pattern.matches(pattern, value);
    }

    public static boolean lengthBetween(String value, int minimumLength, int maximumLength) {
        String normalizedValue = StringUtils.normalize(value);
        return normalizedValue.length() >= minimumLength && normalizedValue.length() <= maximumLength;
    }

    public static boolean hasDigit(String value) {
        return value != null && value.chars().anyMatch(Character::isDigit);
    }

    public static boolean hasDigit(char[] value) {
        return contains(value, Character::isDigit);
    }

    public static boolean hasUppercase(String value) {
        return value != null && value.chars().anyMatch(Character::isUpperCase);
    }

    public static boolean hasUppercase(char[] value) {
        return contains(value, Character::isUpperCase);
    }

    public static boolean hasLowercase(String value) {
        return value != null && value.chars().anyMatch(Character::isLowerCase);
    }

    public static boolean hasLowercase(char[] value) {
        return contains(value, Character::isLowerCase);
    }

    public static boolean hasSpecialCharacter(String value) {
        return value != null && value.chars().anyMatch(character -> !Character.isLetterOrDigit(character));
    }

    public static boolean hasSpecialCharacter(char[] value) {
        return contains(value, character -> !Character.isLetterOrDigit(character));
    }

    private static boolean contains(char[] value, CharacterRule rule) {
        if (value == null) {
            return false;
        }
        for (char character : value) {
            if (rule.matches(character)) {
                return true;
            }
        }
        return false;
    }

    @FunctionalInterface
    private interface CharacterRule {
        boolean matches(char character);
    }
}