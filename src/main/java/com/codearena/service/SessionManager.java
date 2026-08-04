package com.codearena.service;

import com.codearena.model.Candidate;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Holds the current in-memory candidate session until persistent session support is introduced.
 */
public class SessionManager {

    private Candidate currentCandidate;
    private LocalDateTime loginTime;
    private boolean rememberMe;

    public void startSession(Candidate candidate, boolean rememberMe) {
        this.currentCandidate = candidate;
        this.loginTime = LocalDateTime.now();
        this.rememberMe = rememberMe;
    }

    public void logout() {
        this.currentCandidate = null;
        this.loginTime = null;
        this.rememberMe = false;
    }

    public Optional<Candidate> getCurrentCandidate() {
        return Optional.ofNullable(currentCandidate);
    }

    public Optional<LocalDateTime> getLoginTime() {
        return Optional.ofNullable(loginTime);
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public boolean isAuthenticated() {
        return currentCandidate != null;
    }
}
