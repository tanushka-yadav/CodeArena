package com.codearena.service;

import com.codearena.dto.RegistrationRequest;
import com.codearena.dto.RegistrationResponse;

/**
 * Registration use-case boundary.
 */

public interface RegistrationService {

    RegistrationResponse registerCandidate(RegistrationRequest request);

}
