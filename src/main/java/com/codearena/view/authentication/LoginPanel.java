package com.codearena.view.authentication;

import com.codearena.dto.LoginRequest;
import com.codearena.view.components.FooterPanel;
import com.codearena.view.components.FormLabel;
import com.codearena.view.components.HeaderPanel;
import com.codearena.view.components.RoundedButton;
import com.codearena.view.components.RoundedPasswordField;
import com.codearena.view.components.RoundedTextField;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

/**
 * Candidate login form view.
 */
public class LoginPanel extends JPanel {

    private final RoundedTextField usernameOrEmailField;
    private final RoundedPasswordField passwordField;
    private final JCheckBox rememberMeCheckBox;
    private final JLabel errorLabel;
    private final RoundedButton loginButton;
    private final RoundedButton clearButton;
    private final RoundedButton backButton;
    private final javax.swing.JButton registerLinkButton;
    private final javax.swing.JButton forgotPasswordLinkButton;

    public LoginPanel() {
        this.usernameOrEmailField = new RoundedTextField(24);
        this.passwordField = new RoundedPasswordField(24);
        this.rememberMeCheckBox = new JCheckBox("Remember me");
        this.errorLabel = new JLabel(" ");
        this.loginButton = RoundedButton.primary("Login");
        this.clearButton = RoundedButton.secondary("Clear");
        this.backButton = RoundedButton.secondary("Back");
        this.registerLinkButton = createLinkButton("Register");
        this.forgotPasswordLinkButton = createLinkButton("Forgot Password");

        buildLayout();
        configureAccessibility();
    }

    public LoginRequest getLoginRequest() {
        return new LoginRequest(
                usernameOrEmailField.getText(),
                passwordField.getPassword(),
                rememberMeCheckBox.isSelected()
        );
    }

    public void clearForm() {
        usernameOrEmailField.setText("");
        passwordField.setText("");
        rememberMeCheckBox.setSelected(false);
        setErrorMessage(" ");
        usernameOrEmailField.requestFocusInWindow();
    }

    public void setErrorMessage(String message) {
        errorLabel.setText(message == null || message.isBlank() ? " " : message);
    }

    public void onLogin(ActionListener actionListener) {
        loginButton.addActionListener(actionListener);
        passwordField.addActionListener(actionListener);
    }

    public void onClear(ActionListener actionListener) {
        clearButton.addActionListener(actionListener);
    }

    public void onBack(ActionListener actionListener) {
        backButton.addActionListener(actionListener);
    }

    public void onRegister(ActionListener actionListener) {
        registerLinkButton.addActionListener(actionListener);
    }

    public void onForgotPassword(ActionListener actionListener) {
        forgotPasswordLinkButton.addActionListener(actionListener);
    }

    private void buildLayout() {
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(36, 44, 20, 44));

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(24, 36, 12, 36));

        addField(formPanel, "Username or Email", usernameOrEmailField, 0);
        addField(formPanel, "Password", passwordField, 1);
        addRememberMe(formPanel);

        errorLabel.setForeground(new Color(185, 28, 28));
        errorLabel.setAlignmentX(CENTER_ALIGNMENT);

        centerPanel.add(new HeaderPanel("Candidate Login", "Access your CodeArena candidate workspace"));
        centerPanel.add(Box.createRigidArea(new Dimension(0, 22)));
        centerPanel.add(formPanel);
        centerPanel.add(errorLabel);
        centerPanel.add(buildButtonPanel());
        centerPanel.add(buildLinkPanel());

        add(centerPanel, BorderLayout.CENTER);
        add(new FooterPanel(), BorderLayout.SOUTH);
    }

    private JPanel buildButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 14));
        buttonPanel.setOpaque(false);

        Dimension buttonSize = new Dimension(124, 40);
        loginButton.setPreferredSize(buttonSize);
        clearButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(buttonSize);

        buttonPanel.add(loginButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(backButton);
        return buttonPanel;
    }

    private JPanel buildLinkPanel() {
        JPanel linkPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 4));
        linkPanel.setOpaque(false);
        linkPanel.add(registerLinkButton);
        linkPanel.add(forgotPasswordLinkButton);
        return linkPanel;
    }

    private void addField(JPanel panel, String labelText, java.awt.Component inputComponent, int row) {
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = row;
        labelConstraints.anchor = GridBagConstraints.LINE_END;
        labelConstraints.insets = new Insets(8, 8, 8, 14);
        panel.add(new FormLabel(labelText), labelConstraints);

        GridBagConstraints inputConstraints = new GridBagConstraints();
        inputConstraints.gridx = 1;
        inputConstraints.gridy = row;
        inputConstraints.weightx = 1.0;
        inputConstraints.fill = GridBagConstraints.HORIZONTAL;
        inputConstraints.insets = new Insets(8, 0, 8, 8);
        inputComponent.setPreferredSize(new Dimension(320, 38));
        panel.add(inputComponent, inputConstraints);
    }

    private void addRememberMe(JPanel panel) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.insets = new Insets(2, 0, 8, 8);
        rememberMeCheckBox.setOpaque(false);
        panel.add(rememberMeCheckBox, constraints);
    }

    private javax.swing.JButton createLinkButton(String text) {
        javax.swing.JButton button = new javax.swing.JButton(text);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(true);
        button.setForeground(new Color(37, 99, 235));
        return button;
    }

    private void configureAccessibility() {
        usernameOrEmailField.setName("usernameOrEmailField");
        passwordField.setName("loginPasswordField");
        loginButton.setMnemonic('L');
        clearButton.setMnemonic('C');
        backButton.setMnemonic('B');
        usernameOrEmailField.requestFocusInWindow();
    }
}
