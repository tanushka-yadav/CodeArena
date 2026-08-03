package com.codearena.service.impl;

import com.codearena.dto.RegistrationRequest;
import com.codearena.dto.RegistrationResponse;
import com.codearena.model.Candidate;
import com.codearena.model.CandidateProfile;
import com.codearena.repository.CandidateRepository;
import com.codearena.service.RegistrationService;
import com.codearena.util.StringUtils;
import com.codearena.validator.RegistrationValidator;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Applies registration rules and persists valid candidates through the repository boundary.
 */
public class RegistrationServiceImpl implements RegistrationService {

    private static final int PASSWORD_HASH_ITERATIONS = 120_000;
    private static final int PASSWORD_SALT_BYTES = 16;
    private static final int PASSWORD_HASH_BITS = 256;
    private static final String PASSWORD_HASH_ALGORITHM = "PBKDF2WithHmacSHA256";

    private final CandidateRepository candidateRepository;
    private final RegistrationValidator registrationValidator;
    private final SecureRandom secureRandom;

    public RegistrationServiceImpl(CandidateRepository candidateRepository, RegistrationValidator registrationValidator) {
        this.candidateRepository = Objects.requireNonNull(candidateRepository, "candidateRepository is required");
        this.registrationValidator = Objects.requireNonNull(registrationValidator, "registrationValidator is required");
        this.secureRandom = new SecureRandom();
    }

    @Override
    public RegistrationResponse registerCandidate(RegistrationRequest request) {
        try {
            List<String> errors = new ArrayList<>(registrationValidator.validate(request));

            if (request != null) {
                addDuplicateErrors(request, errors);
            }

            if (!errors.isEmpty()) {
                return RegistrationResponse.failure("Please correct the highlighted registration details.", errors);
            }

            Candidate candidate = toCandidate(request);
            if (!candidateRepository.saveIfUnique(candidate)) {
                return RegistrationResponse.failure(
                        "Please correct the highlighted registration details.",
                        duplicateErrorsFor(request)
                );
            }
            return RegistrationResponse.success("Candidate registered successfully.", candidate);
        } finally {
            if (request != null) {
                request.clearSensitiveData();
            }
        }
    }

    private void addDuplicateErrors(RegistrationRequest request, List<String> errors) {
        if (candidateRepository.existsByUsername(request.getUsername())) {
            errors.add("Username is already registered.");
        }
        if (candidateRepository.existsByEmailAddress(request.getEmailAddress())) {
            errors.add("Email address is already registered.");
        }
    }

    private List<String> duplicateErrorsFor(RegistrationRequest request) {
        List<String> errors = new ArrayList<>();
        addDuplicateErrors(request, errors);
        if (errors.isEmpty()) {
            errors.add("Username or email address is already registered.");
        }
        return errors;
    }

    private Candidate toCandidate(RegistrationRequest request) {
        CandidateProfile profile = new CandidateProfile(
                StringUtils.normalize(request.getFullName()),
                StringUtils.normalize(request.getMobileNumber()),
                request.getGender(),
                request.getDateOfBirth()
        );

        return new Candidate(
                StringUtils.normalize(request.getUsername()),
                StringUtils.normalize(request.getEmailAddress()).toLowerCase(Locale.ROOT),
                hashPassword(request.getPassword()),
                profile
        );
    }

    private String hashPassword(char[] password) {
        byte[] salt = new byte[PASSWORD_SALT_BYTES];
        secureRandom.nextBytes(salt);
        PBEKeySpec keySpec = new PBEKeySpec(password, salt, PASSWORD_HASH_ITERATIONS, PASSWORD_HASH_BITS);
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PASSWORD_HASH_ALGORITHM);
            byte[] hash = keyFactory.generateSecret(keySpec).getEncoded();
            Base64.Encoder encoder = Base64.getEncoder();
            return PASSWORD_HASH_ALGORITHM
                    + "$" + PASSWORD_HASH_ITERATIONS
                    + "$" + encoder.encodeToString(salt)
                    + "$" + encoder.encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException exception) {
            throw new IllegalStateException("Password hashing is not available.", exception);
        } finally {
            keySpec.clearPassword();
            Arrays.fill(password, '\0');
            Arrays.fill(salt, (byte) 0);
        }
    }
}