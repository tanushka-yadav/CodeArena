package com.codearena.view.authentication;

import com.codearena.constants.ApplicationConstants;

import javax.swing.JFrame;
import java.awt.Dimension;

/**
 * Standalone candidate login window.
 */
public class LoginFrame extends JFrame {

    private final LoginPanel loginPanel;

    public LoginFrame() {
        super("Candidate Login - " + ApplicationConstants.APP_NAME);
        this.loginPanel = new LoginPanel();
        configureWindow();
    }

    public LoginPanel getLoginPanel() {
        return loginPanel;
    }

    private void configureWindow() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(780, 560));
        setSize(860, 620);
        setLocationRelativeTo(null);
        setContentPane(loginPanel);
    }
}
