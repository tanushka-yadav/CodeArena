package com.codearena.service;

import com.codearena.dto.AuthenticationResult;
import com.codearena.dto.LoginRequest;

/**
 * Authentication use-case boundary for candidate login.
 */
public interface AuthenticationService {

    AuthenticationResult authenticate(LoginRequest request);
}
