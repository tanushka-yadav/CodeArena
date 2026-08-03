package com.codearena.controller;

import com.codearena.view.MainFrame;
import com.codearena.view.registration.RegistrationFrame;
import com.codearena.view.registration.RegistrationPanel;

import java.util.Objects;

/**
 * Coordinates navigation between the main application shell and feature windows.
 */
public class NavigationController {

    private final MainFrame mainFrame;
    private final RegistrationFrame registrationFrame;

    public NavigationController(MainFrame mainFrame, RegistrationFrame registrationFrame) {
        this.mainFrame = Objects.requireNonNull(mainFrame, "mainFrame is required");
        this.registrationFrame = Objects.requireNonNull(registrationFrame, "registrationFrame is required");
    }

    /**
     * Shows the main application welcome screen and closes any registration workflow window.
     */
    public void showWelcome() {
        registrationFrame.setVisible(false);
        mainFrame.showWelcomePanel();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        mainFrame.toFront();
        mainFrame.requestFocus();
    }

    /**
     * Opens candidate registration as its own feature window.
     */
    public void showRegistration() {
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

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public RegistrationFrame getRegistrationFrame() {
        return registrationFrame;
    }

    public void setRegistrationPanel(RegistrationPanel registrationPanel) {
    }
}
