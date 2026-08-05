package com.codearena.repository;

import com.codearena.model.Candidate;

import java.util.List;
import java.util.Optional;

/**
 * Candidate persistence boundary used by services.
 */
public interface CandidateRepository {

    boolean saveIfUnique(Candidate candidate);

    Candidate save(Candidate candidate);

    boolean existsByUsername(String username);

    boolean existsByEmailAddress(String emailAddress);

    Optional<Candidate> findByUsername(String username);

    Optional<Candidate> findByEmailAddress(String emailAddress);

    Optional<Candidate> findByUsernameOrEmail(String usernameOrEmail);

    List<Candidate> findAll();
}
