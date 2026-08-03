package com.codearena.dto;

import com.codearena.model.Candidate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Represents the result of a candidate registration attempt.
 */

public class RegistrationResponse {

    private final boolean successful;
    private final String message;
    private final Candidate candidate;
    private final List<String> errors;

    private RegistrationResponse(boolean successful, String message, Candidate candidate, List<String> errors) {
        this.successful = successful;
        this.message = message;
        this.candidate = candidate;
        this.errors = new ArrayList<>(errors);
    }

    public static RegistrationResponse success(String message, Candidate candidate) {
        return new RegistrationResponse(true, message, candidate, Collections.emptyList());
    }

    public static RegistrationResponse failure(String message, List<String> errors) {
        return new RegistrationResponse(false, message, null, errors);
    }

    public boolean isSuccessful() {
        return successful;
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
