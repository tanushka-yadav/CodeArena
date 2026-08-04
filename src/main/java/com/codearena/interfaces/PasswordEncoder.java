package com.codearena.interfaces;

/**
 * Encodes and verifies passwords without exposing storage details to services.
 */
public interface PasswordEncoder {

    String encode(char[] rawPassword);

    boolean matches(char[] rawPassword, String encodedPassword);
}
