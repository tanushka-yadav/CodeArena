package com.codearena.view.registration;

import com.codearena.dto.RegistrationRequest;
import com.codearena.enums.Gender;
import com.codearena.util.DateTimeUtil;
import com.codearena.view.components.FooterPanel;
import com.codearena.view.components.FormLabel;
import com.codearena.view.components.HeaderPanel;
import com.codearena.view.components.RoundedButton;
import com.codearena.view.components.RoundedPasswordField;
import com.codearena.view.components.RoundedTextField;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Candidate registration form view.
 */
public class RegistrationPanel extends JPanel {

    private final RoundedTextField fullNameField;
    private final RoundedTextField usernameField;
    private final RoundedTextField emailField;
    private final RoundedTextField mobileNumberField;
    private final RoundedPasswordField passwordField;
    private final RoundedPasswordField confirmPasswordField;
    private final JComboBox<Gender> genderComboBox;
    private final RoundedTextField dateOfBirthField;
    private final RoundedButton registerButton;
    private final RoundedButton resetButton;
    private final RoundedButton backButton;

    public RegistrationPanel() {
        this.fullNameField = new RoundedTextField(24);
        this.usernameField = new RoundedTextField(24);
        this.emailField = new RoundedTextField(24);
        this.mobileNumberField = new RoundedTextField(24);
        this.passwordField = new RoundedPasswordField(24);
        this.confirmPasswordField = new RoundedPasswordField(24);
        this.genderComboBox = new JComboBox<>(Gender.values());
        this.dateOfBirthField = new RoundedTextField(24);
        this.registerButton = RoundedButton.primary("Register");
        this.resetButton = RoundedButton.secondary("Reset");
        this.backButton = RoundedButton.secondary("Back");

        buildLayout();
        configureAccessibility();
    }

    public RegistrationRequest getRegistrationRequest() {
        String dateOfBirthInput = dateOfBirthField.getText();
        Optional<LocalDate> dateOfBirth = DateTimeUtil.parseDate(dateOfBirthInput);
        return new RegistrationRequest(
                fullNameField.getText(),
                usernameField.getText(),
                emailField.getText(),
                mobileNumberField.getText(),
                passwordField.getPassword(),
                confirmPasswordField.getPassword(),
                (Gender) genderComboBox.getSelectedItem(),
                dateOfBirthInput,
                dateOfBirth.orElse(null)
        );
    }

    public void clearForm() {
        fullNameField.setText("");
        usernameField.setText("");
        emailField.setText("");
        mobileNumberField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        genderComboBox.setSelectedIndex(0);
        dateOfBirthField.setText("");
        fullNameField.requestFocusInWindow();
    }

    public void onRegister(ActionListener actionListener) {
        registerButton.addActionListener(actionListener);
        passwordField.addActionListener(actionListener);
        confirmPasswordField.addActionListener(actionListener);
    }

    public void onReset(ActionListener actionListener) {
        resetButton.addActionListener(actionListener);
    }

    public void onBack(ActionListener actionListener) {
        backButton.addActionListener(actionListener);
    }

    private void buildLayout() {
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(28, 44, 20, 44));

        JPanel formCard = new JPanel(new BorderLayout());
        formCard.setOpaque(false);
        formCard.setBorder(BorderFactory.createEmptyBorder(12, 36, 12, 36));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);

        addField(formPanel, "Full Name", fullNameField, 0);
        addField(formPanel, "Username", usernameField, 1);
        addField(formPanel, "Email Address", emailField, 2);
        addField(formPanel, "Mobile Number", mobileNumberField, 3);
        addField(formPanel, "Password", passwordField, 4);
        addField(formPanel, "Confirm Password", confirmPasswordField, 5);
        addField(formPanel, "Gender", genderComboBox, 6);
        addField(formPanel, "Date of Birth (" + DateTimeUtil.inputPattern() + ")", dateOfBirthField, 7);

        formCard.add(formPanel, BorderLayout.CENTER);
        formCard.add(buildButtonPanel(), BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(new HeaderPanel("Candidate Registration", "Create your CodeArena candidate account"));
        centerPanel.add(Box.createRigidArea(new Dimension(0, 22)));
        centerPanel.add(formCard);

        add(centerPanel, BorderLayout.CENTER);
        add(new FooterPanel(), BorderLayout.SOUTH);
    }

    private JPanel buildButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 20));
        buttonPanel.setOpaque(false);

        Dimension buttonSize = new Dimension(132, 40);
        registerButton.setPreferredSize(buttonSize);
        resetButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(buttonSize);

        buttonPanel.add(registerButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(backButton);
        return buttonPanel;
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

    private void configureAccessibility() {
        fullNameField.setName("fullNameField");
        usernameField.setName("usernameField");
        emailField.setName("emailField");
        mobileNumberField.setName("mobileNumberField");
        passwordField.setName("passwordField");
        confirmPasswordField.setName("confirmPasswordField");
        dateOfBirthField.setName("dateOfBirthField");
        registerButton.setMnemonic('R');
        resetButton.setMnemonic('S');
        backButton.setMnemonic('B');
    }
}