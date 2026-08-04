package com.codearena.controller;

import com.codearena.view.authentication.LoginFrame;
import com.codearena.view.MainFrame;
import com.codearena.view.dashboard.DashboardPanel;
import com.codearena.view.dashboard.PlaceholderFrame;
import com.codearena.view.registration.RegistrationFrame;

import java.util.Objects;

/**
 * Coordinates navigation between the main application shell and feature windows.
 */
public class NavigationController {

    private final MainFrame mainFrame;
    private final LoginFrame loginFrame;
    private final RegistrationFrame registrationFrame;
    private DashboardPanel dashboardPanel;

    public NavigationController(MainFrame mainFrame, LoginFrame loginFrame, RegistrationFrame registrationFrame) {
        this.mainFrame = Objects.requireNonNull(mainFrame, "mainFrame is required");
        this.loginFrame = Objects.requireNonNull(loginFrame, "loginFrame is required");
        this.registrationFrame = Objects.requireNonNull(registrationFrame, "registrationFrame is required");
    }

    public void setDashboardPanel(DashboardPanel dashboardPanel) {
        this.dashboardPanel = Objects.requireNonNull(dashboardPanel, "dashboardPanel is required");
    }

    /**
     * Shows the main application welcome screen and closes any registration workflow window.
     */
    public void showWelcome() {
        loginFrame.setVisible(false);
        registrationFrame.setVisible(false);
        mainFrame.showWelcomePanel();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        mainFrame.toFront();
        mainFrame.requestFocus();
    }

    /**
     * Opens candidate login as its own feature window.
     */
    public void showLogin() {
        registrationFrame.setVisible(false);
        loginFrame.setLocationRelativeTo(mainFrame);
        loginFrame.setVisible(true);
        loginFrame.toFront();
        loginFrame.requestFocus();
    }

    /**
     * Opens candidate registration as its own feature window.
     */
    public void showRegistration() {
        loginFrame.setVisible(false);
        registrationFrame.setLocationRelativeTo(mainFrame);
        registrationFrame.setVisible(true);
        registrationFrame.toFront();
        registrationFrame.requestFocus();
    }

    /**
     * Explicit alias for flows that need a standalone registration window.
     */
    public void showRegistrationWindow() {
        showRegistration();
    }

    /**
     * Displays the candidate dashboard inside the main application shell.
     */
    public void showDashboard(DashboardPanel dashboardPanel) {
        this.dashboardPanel = Objects.requireNonNull(dashboardPanel, "dashboardPanel is required");
        loginFrame.setVisible(false);
        registrationFrame.setVisible(false);
        mainFrame.showScreen(dashboardPanel);
        mainFrame.setVisible(true);
        mainFrame.toFront();
        mainFrame.requestFocus();
    }

    public void showDashboard() {
        if (dashboardPanel == null) {
            throw new IllegalStateException("Dashboard panel has not been configured.");
        }
        showDashboard(dashboardPanel);
    }

    public void showPlaceholder(String title, String message) {
        PlaceholderFrame placeholderFrame = new PlaceholderFrame(title, message);
        placeholderFrame.setLocationRelativeTo(mainFrame);
        placeholderFrame.setVisible(true);
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public LoginFrame getLoginFrame() {
        return loginFrame;
    }

    public RegistrationFrame getRegistrationFrame() {
        return registrationFrame;
    }
}
