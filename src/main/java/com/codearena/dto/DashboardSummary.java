package com.codearena.dto;

import com.codearena.model.Candidate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Read-only dashboard data prepared by the service layer for the view.
 */
public class DashboardSummary {

    private final Candidate candidate;
    private final LocalDate currentDate;
    private final LocalTime currentTime;
    private final LocalDateTime loginTime;
    private final boolean authenticated;
    private final String applicationVersion;
    private final String sessionLabel;

    public DashboardSummary(Candidate candidate, LocalDate currentDate, LocalTime currentTime,
                            LocalDateTime loginTime, boolean authenticated, String applicationVersion,
                            String sessionLabel) {
        this.candidate = Objects.requireNonNull(candidate, "candidate is required");
        this.currentDate = Objects.requireNonNull(currentDate, "currentDate is required");
        this.currentTime = Objects.requireNonNull(currentTime, "currentTime is required");
        this.loginTime = Objects.requireNonNull(loginTime, "loginTime is required");
        this.authenticated = authenticated;
        this.applicationVersion = Objects.requireNonNull(applicationVersion, "applicationVersion is required");
        this.sessionLabel = Objects.requireNonNull(sessionLabel, "sessionLabel is required");
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public String getCandidateName() {
        return candidate.getProfile().getFullName();
    }

    public String getUsername() {
        return candidate.getUsername();
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public LocalTime getCurrentTime() {
        return currentTime;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public String getSessionLabel() {
        return sessionLabel;
    }
}
