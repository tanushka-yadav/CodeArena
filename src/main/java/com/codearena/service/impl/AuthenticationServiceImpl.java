package com.codearena.service.impl;

import com.codearena.dto.AuthenticationResult;
import com.codearena.dto.LoginRequest;
import com.codearena.exception.DatabaseException;
import com.codearena.interfaces.PasswordEncoder;
import com.codearena.model.Candidate;
import com.codearena.repository.CandidateRepository;
import com.codearena.service.AuthenticationService;
import com.codearena.service.SessionManager;
import com.codearena.util.StringUtils;
import com.codearena.validator.CredentialValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Authenticates candidates using repository lookup and encoded password verification.
 */
public class AuthenticationServiceImpl implements AuthenticationService {

    private final CandidateRepository candidateRepository;
    private final CredentialValidator credentialValidator;
    private final PasswordEncoder passwordEncoder;
    private final SessionManager sessionManager;

    public AuthenticationServiceImpl(CandidateRepository candidateRepository, CredentialValidator credentialValidator,
                                     PasswordEncoder passwordEncoder, SessionManager sessionManager) {
        this.candidateRepository = Objects.requireNonNull(candidateRepository, "candidateRepository is required");
        this.credentialValidator = Objects.requireNonNull(credentialValidator, "credentialValidator is required");
        this.passwordEncoder = Objects.requireNonNull(passwordEncoder, "passwordEncoder is required");
        this.sessionManager = Objects.requireNonNull(sessionManager, "sessionManager is required");
    }

    @Override
    public AuthenticationResult authenticate(LoginRequest request) {
        try {
            List<String> errors = new ArrayList<>(credentialValidator.validate(request));
            if (!errors.isEmpty()) {
                return AuthenticationResult.failure("Please correct the login details.", errors);
            }

            return candidateRepository.findByUsernameOrEmail(StringUtils.normalize(request.getUsernameOrEmail()))
                    .filter(candidate -> passwordMatches(request, candidate))
                    .map(candidate -> startSession(request, candidate))
                    .orElseGet(this::invalidCredentials);
        } catch (DatabaseException exception) {
            return AuthenticationResult.failure(
                    "Login is temporarily unavailable.",
                    List.of("Unable to verify credentials right now. Please try again later.")
            );
        } finally {
            if (request != null) {
                request.clearSensitiveData();
            }
        }
    }

    private boolean passwordMatches(LoginRequest request, Candidate candidate) {
        char[] password = request.getPassword();
        try {
            return passwordEncoder.matches(password, candidate.getPasswordHash());
        } finally {
            Arrays.fill(password, '\0');
        }
    }

    private AuthenticationResult startSession(LoginRequest request, Candidate candidate) {
        sessionManager.startSession(candidate, request.isRememberMe());
        return AuthenticationResult.success("Login successful.", candidate);
    }

    private AuthenticationResult invalidCredentials() {
        return AuthenticationResult.failure(
                "Invalid username/email or password.",
                List.of("Invalid username/email or password.")
        );
    }
}
