package com.codearena.model;

import com.codearena.enums.UserRole;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Domain model for a registered candidate.
 */

public class Candidate {

    private final String candidateId;
    private String username;
    private String emailAddress;
    private String passwordHash;
    private UserRole role;
    private CandidateProfile profile;
    private final LocalDateTime createdAt;

    public Candidate(String username, String emailAddress, String passwordHash, CandidateProfile profile) {
        this.candidateId = UUID.randomUUID().toString();
        this.username = Objects.requireNonNull(username, "username is required");
        this.emailAddress = Objects.requireNonNull(emailAddress, "emailAddress is required");
        this.passwordHash = Objects.requireNonNull(passwordHash, "passwordHash is required");
        this.profile = Objects.requireNonNull(profile, "profile is required");
        this.role = UserRole.CANDIDATE;
        this.createdAt = LocalDateTime.now();
    }

    public String getCandidateId() {
        return candidateId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = Objects.requireNonNull(username, "username is required");
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = Objects.requireNonNull(emailAddress, "emailAddress is required");
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = Objects.requireNonNull(passwordHash, "passwordHash is required");
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = Objects.requireNonNull(role, "role is required");
    }

    public CandidateProfile getProfile() {
        return profile;
    }

    public void setProfile(CandidateProfile profile) {
        this.profile = Objects.requireNonNull(profile, "profile is required");
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


}
