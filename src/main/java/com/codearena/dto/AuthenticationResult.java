package com.codearena.dto;

import com.codearena.model.Candidate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Represents the outcome of a candidate authentication attempt.
 */
public class AuthenticationResult {

    private final boolean authenticated;
    private final String message;
    private final Candidate candidate;
    private final List<String> errors;

    private AuthenticationResult(boolean authenticated, String message, Candidate candidate, List<String> errors) {
        this.authenticated = authenticated;
        this.message = message;
        this.candidate = candidate;
        this.errors = new ArrayList<>(errors);
    }

    public static AuthenticationResult success(String message, Candidate candidate) {
        return new AuthenticationResult(true, message, candidate, Collections.emptyList());
    }

    public static AuthenticationResult failure(String message, List<String> errors) {
        return new AuthenticationResult(false, message, null, errors);
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public String getMessage() {
        return message;
    }

    public Optional<Candidate> getCandidate() {
        return Optional.ofNullable(candidate);
    }

    public List<String> getErrors() {
        return Collections.unmodifiableList(errors);
    }
}
