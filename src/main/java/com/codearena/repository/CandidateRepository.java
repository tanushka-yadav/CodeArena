package com.codearena.repository;

import com.codearena.model.Candidate;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Stores candidates behind repository methods that can later be implemented with JDBC.
 */
public class CandidateRepository {

    private final ConcurrentMap<String, Candidate> candidatesByUsername;
    private final ConcurrentMap<String, String> usernamesByEmail;

    public CandidateRepository() {
        this.candidatesByUsername = new ConcurrentHashMap<>();
        this.usernamesByEmail = new ConcurrentHashMap<>();
    }

    /**
     * Saves the candidate only when both username and email are still available.
     */
    public synchronized boolean saveIfUnique(Candidate candidate) {
        String usernameKey = normalizeKey(candidate.getUsername());
        String emailKey = normalizeKey(candidate.getEmailAddress());

        if (candidatesByUsername.containsKey(usernameKey) || usernamesByEmail.containsKey(emailKey)) {
            return false;
        }

        candidatesByUsername.put(usernameKey, candidate);
        usernamesByEmail.put(emailKey, usernameKey);
        return true;
    }

    public Candidate save(Candidate candidate) {
        if (!saveIfUnique(candidate)) {
            throw new IllegalArgumentException("Candidate username or email address already exists.");
        }
        return candidate;
    }

    public boolean existsByUsername(String username) {
        return candidatesByUsername.containsKey(normalizeKey(username));
    }

    public boolean existsByEmailAddress(String emailAddress) {
        return usernamesByEmail.containsKey(normalizeKey(emailAddress));
    }

    public Optional<Candidate> findByUsername(String username) {
        return Optional.ofNullable(candidatesByUsername.get(normalizeKey(username)));
    }

    public Optional<Candidate> findByEmailAddress(String emailAddress) {
        String usernameKey = usernamesByEmail.get(normalizeKey(emailAddress));
        if (usernameKey == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(candidatesByUsername.get(usernameKey));
    }

    public Optional<Candidate> findByUsernameOrEmail(String usernameOrEmail) {
        Optional<Candidate> candidate = findByUsername(usernameOrEmail);
        return candidate.isPresent() ? candidate : findByEmailAddress(usernameOrEmail);
    }

    public List<Candidate> findAll() {
        return new ArrayList<>(candidatesByUsername.values());
    }

    private String normalizeKey(String value) {
        return value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
    }
}
