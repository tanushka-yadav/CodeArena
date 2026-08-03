package com.codearena.view.registration;
import com.codearena.constants.ApplicationConstants;

import javax.swing.JFrame;
import java.awt.Dimension;

/**
 * Standalone registration window for flows that open registration outside the main shell.
 */
public class RegistrationFrame extends JFrame {

    private final RegistrationPanel registrationPanel;

    public RegistrationFrame() {
        super("Candidate Registration - " + ApplicationConstants.APP_NAME);
        this.registrationPanel = new RegistrationPanel();
        configureWindow();
    }

    public RegistrationPanel getRegistrationPanel() {
        return registrationPanel;
    }

    private void configureWindow() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(820, 620));
        setSize(920, 680);
        setLocationRelativeTo(null);
        setContentPane(registrationPanel);
    }}
