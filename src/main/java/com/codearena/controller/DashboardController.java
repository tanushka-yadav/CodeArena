package com.codearena.controller;

import com.codearena.dto.DashboardSummary;
import com.codearena.service.DashboardService;
import com.codearena.service.SessionManager;
import com.codearena.util.DialogUtils;
import com.codearena.view.dashboard.DashboardPanel;

import java.util.Objects;

/**
 * Coordinates candidate dashboard rendering and dashboard navigation actions.
 */
public class DashboardController {

    private final DashboardService dashboardService;
    private final SessionManager sessionManager;
    private final DashboardPanel dashboardPanel;
    private final NavigationController navigationController;

    public DashboardController(DashboardService dashboardService, SessionManager sessionManager,
                               DashboardPanel dashboardPanel, NavigationController navigationController) {
        this.dashboardService = Objects.requireNonNull(dashboardService, "dashboardService is required");
        this.sessionManager = Objects.requireNonNull(sessionManager, "sessionManager is required");
        this.dashboardPanel = Objects.requireNonNull(dashboardPanel, "dashboardPanel is required");
        this.navigationController = Objects.requireNonNull(navigationController, "navigationController is required");
        bindActions();
    }

    public void showDashboard() {
        try {
            DashboardSummary summary = dashboardService.loadDashboardSummary();
            dashboardPanel.render(summary);
            navigationController.showDashboard(dashboardPanel);
        } catch (RuntimeException exception) {
            DialogUtils.showError(dashboardPanel, "Dashboard could not be loaded. Please log in again.");
            navigationController.showLogin();
        }
    }

    private void bindActions() {
        dashboardPanel.onStartTest(event -> navigationController.showPlaceholder(
                "Coding Test",
                "Coding test engine will be implemented in a future step."
        ));
        dashboardPanel.onResults(event -> navigationController.showPlaceholder(
                "Previous Results",
                "Candidate results and performance reports will be implemented in a future step."
        ));
        dashboardPanel.onLeaderboard(event -> navigationController.showPlaceholder(
                "Leaderboard",
                "Leaderboard rankings will be implemented in a future step."
        ));
        dashboardPanel.onProfile(event -> navigationController.showPlaceholder(
                "My Profile",
                "Candidate profile management will be implemented in a future step."
        ));
        dashboardPanel.onSettings(event -> navigationController.showPlaceholder(
                "Settings",
                "Candidate settings will be implemented in a future step."
        ));
        dashboardPanel.onHelp(event -> navigationController.showPlaceholder(
                "Help",
                "Help and support content will be implemented in a future step."
        ));
        dashboardPanel.onLogout(event -> logout());
    }

    private void logout() {
        sessionManager.logout();
        navigationController.showWelcome();
    }
}
