package com.codearena.util;
/**
 * Small string helpers used by validation and form mapping.
 */
public final class StringUtils {

    private StringUtils() {
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static String normalize(String value) {
        return value == null ? "" : value.trim();
    }

    public static boolean containsOuterWhitespace(String value) {
        return value != null && !value.equals(value.trim());
    }
}
