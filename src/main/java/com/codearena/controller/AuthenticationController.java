package com.codearena.controller;

import com.codearena.dto.AuthenticationResult;
import com.codearena.service.AuthenticationService;
import com.codearena.util.DialogUtils;
import com.codearena.view.authentication.LoginPanel;

import java.util.Objects;

/**
 * Handles candidate login actions and delegates authentication to the service layer.
 */
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final LoginPanel loginPanel;
    private final NavigationController navigationController;

    public AuthenticationController(AuthenticationService authenticationService, LoginPanel loginPanel,
                                    NavigationController navigationController) {
        this.authenticationService = Objects.requireNonNull(authenticationService, "authenticationService is required");
        this.loginPanel = Objects.requireNonNull(loginPanel, "loginPanel is required");
        this.navigationController = Objects.requireNonNull(navigationController, "navigationController is required");
        bindActions();
    }

    private void bindActions() {
        loginPanel.onLogin(event -> loginCandidate());
        loginPanel.onClear(event -> loginPanel.clearForm());
        loginPanel.onBack(event -> navigationController.showWelcome());
        loginPanel.onRegister(event -> navigationController.showRegistration());
        loginPanel.onForgotPassword(event -> DialogUtils.showSuccess(
                loginPanel,
                "Forgot password workflow will be available in a future step."
        ));
    }

    private void loginCandidate() {
        try {
            AuthenticationResult result = authenticationService.authenticate(loginPanel.getLoginRequest());
            if (result.isAuthenticated()) {
                loginPanel.clearForm();
                navigationController.showCandidateDashboard(result.getCandidate().orElseThrow());
            } else {
                loginPanel.setErrorMessage(result.getMessage());
                DialogUtils.showValidationErrors(loginPanel, result.getErrors());
            }
        } catch (RuntimeException exception) {
            DialogUtils.showError(loginPanel, "Login failed due to a system error. Please try again.");
        }
    }
}
