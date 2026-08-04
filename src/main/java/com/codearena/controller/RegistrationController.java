package com.codearena.controller;

import com.codearena.dto.RegistrationResponse;
import com.codearena.service.RegistrationService;
import com.codearena.util.DialogUtils;
import com.codearena.view.registration.RegistrationPanel;

import java.util.Objects;

/**
 * Handles user actions from the candidate registration view.
 */
public class RegistrationController {

    private final RegistrationService registrationService;
    private final RegistrationPanel registrationPanel;
    private final NavigationController navigationController;

    public RegistrationController(RegistrationService registrationService, RegistrationPanel registrationPanel,
                                  NavigationController navigationController) {
        this.registrationService = Objects.requireNonNull(registrationService, "registrationService is required");
        this.registrationPanel = Objects.requireNonNull(registrationPanel, "registrationPanel is required");
        this.navigationController = Objects.requireNonNull(navigationController, "navigationController is required");
        bindActions();
    }

    private void bindActions() {
        registrationPanel.onRegister(event -> registerCandidate());
        registrationPanel.onReset(event -> registrationPanel.clearForm());
        registrationPanel.onBack(event -> navigationController.showWelcome());
    }

    private void registerCandidate() {
        registrationPanel.setRegistrationInProgress(true);
        try {
            RegistrationResponse response = registrationService.registerCandidate(registrationPanel.getRegistrationRequest());
            if (response.isSuccessful()) {
                DialogUtils.showSuccess(registrationPanel, response.getMessage());
                registrationPanel.clearForm();
                navigationController.showLogin();
            } else {
                DialogUtils.showValidationErrors(registrationPanel, response.getErrors());
            }
        } catch (RuntimeException exception) {
            DialogUtils.showError(registrationPanel, "Registration failed due to a system error. Please try again.");
        } finally {
            registrationPanel.setRegistrationInProgress(false);
        }
    }
}
