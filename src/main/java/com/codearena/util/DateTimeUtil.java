package com.codearena.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.Optional;

/**
 * Date parsing and age calculation helpers.
 */

public final class DateTimeUtil {

    private static final DateTimeFormatter DATE_INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DISPLAY_DATE_FORMAT = DateTimeFormatter.ofPattern("MMM dd, yyyy");

    public static String formatDate(LocalDate date) {
        return Objects.requireNonNull(date, "date is required").format(DISPLAY_DATE_FORMAT);
    }

    private void DateUtils() {
    }

    public static Optional<LocalDate> parseDate(String dateValue) {
        if (StringUtils.isBlank(dateValue)) {
            return Optional.empty();
        }

        try {
            return Optional.of(LocalDate.parse(dateValue.trim(), DATE_INPUT_FORMAT));
        } catch (DateTimeParseException exception) {
            return Optional.empty();
        }
    }

    public static int calculateAge(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    public static String inputPattern() {
        return "yyyy-MM-dd";
    }

}
