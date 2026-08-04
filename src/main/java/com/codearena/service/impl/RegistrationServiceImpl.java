package com.codearena.service.impl;

import com.codearena.dto.RegistrationRequest;
import com.codearena.dto.RegistrationResponse;
import com.codearena.interfaces.PasswordEncoder;
import com.codearena.model.Candidate;
import com.codearena.model.CandidateProfile;
import com.codearena.repository.CandidateRepository;
import com.codearena.service.RegistrationService;
import com.codearena.util.Pbkdf2PasswordEncoder;
import com.codearena.util.StringUtils;
import com.codearena.validator.RegistrationValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Applies registration rules and persists valid candidates through the repository boundary.
 */
public class RegistrationServiceImpl implements RegistrationService {

    private final CandidateRepository candidateRepository;
    private final RegistrationValidator registrationValidator;
    private final PasswordEncoder passwordEncoder;

    public RegistrationServiceImpl(CandidateRepository candidateRepository, RegistrationValidator registrationValidator) {
        this(candidateRepository, registrationValidator, new Pbkdf2PasswordEncoder());
    }

    public RegistrationServiceImpl(CandidateRepository candidateRepository, RegistrationValidator registrationValidator,
                                   PasswordEncoder passwordEncoder) {
        this.candidateRepository = Objects.requireNonNull(candidateRepository, "candidateRepository is required");
        this.registrationValidator = Objects.requireNonNull(registrationValidator, "registrationValidator is required");
        this.passwordEncoder = Objects.requireNonNull(passwordEncoder, "passwordEncoder is required");
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

        char[] password = request.getPassword();
        try {
            return new Candidate(
                    StringUtils.normalize(request.getUsername()),
                    StringUtils.normalize(request.getEmailAddress()).toLowerCase(Locale.ROOT),
                    passwordEncoder.encode(password),
                    profile
            );
        } finally {
            Arrays.fill(password, '\0');
        }
    }
}
