package com.codearena.interfaces;

import java.util.List;

/**
 * Contract for validation components that return user-facing error messages.
 *
 * @param <T> value type being validated
 */

public interface Validatable<T> {

    List<String> validate(T value);

    default boolean isValid(T value) {
        return validate(value).isEmpty();
    }

}
