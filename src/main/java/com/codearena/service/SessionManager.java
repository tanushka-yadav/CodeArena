package com.codearena.service;

import com.codearena.model.Candidate;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Holds the current in-memory candidate session until persistent session support is introduced.
 */
public class SessionManager {

    private Candidate currentCandidate;
    private LocalDateTime loginTime;
    private boolean rememberMe;
    private String sessionId;

    public void startSession(Candidate candidate, boolean rememberMe) {
        this.currentCandidate = candidate;
        this.loginTime = LocalDateTime.now();
        this.rememberMe = rememberMe;
        this.sessionId = UUID.randomUUID().toString();
    }

    public void logout() {
        this.currentCandidate = null;
        this.loginTime = null;
        this.rememberMe = false;
        this.sessionId = null;
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

    public String getSessionId() {
        return sessionId == null ? "No active session" : sessionId;
    }

    public boolean isExpired() {
        return false;
    }
}
