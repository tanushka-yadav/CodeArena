package com.codearena.dto;


import com.codearena.enums.Gender;

import java.time.LocalDate;
import java.util.Arrays;

/**
 * Carries candidate registration form values from the view to the service layer.
 */
public class RegistrationRequest {

    private String fullName;
    private String username;
    private String emailAddress;
    private String mobileNumber;
    private char[] password;
    private char[] confirmPassword;
    private Gender gender;
    private String dateOfBirthInput;
    private LocalDate dateOfBirth;

    public RegistrationRequest(String fullName, String username, String emailAddress, String mobileNumber,
                               char[] password, char[] confirmPassword, Gender gender, String dateOfBirthInput,
                               LocalDate dateOfBirth) {
        this.fullName = fullName;
        this.username = username;
        this.emailAddress = emailAddress;
        this.mobileNumber = mobileNumber;
        this.password = copyPassword(password);
        this.confirmPassword = copyPassword(confirmPassword);
        this.gender = gender;
        this.dateOfBirthInput = dateOfBirthInput;
        this.dateOfBirth = dateOfBirth;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public char[] getPassword() {
        return copyPassword(password);
    }

    public void setPassword(char[] password) {
        clear(this.password);
        this.password = copyPassword(password);
    }

    public char[] getConfirmPassword() {
        return copyPassword(confirmPassword);
    }

    public void setConfirmPassword(char[] confirmPassword) {
        clear(this.confirmPassword);
        this.confirmPassword = copyPassword(confirmPassword);
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getDateOfBirthInput() {
        return dateOfBirthInput;
    }

    public void setDateOfBirthInput(String dateOfBirthInput) {
        this.dateOfBirthInput = dateOfBirthInput;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void clearSensitiveData() {
        clear(password);
        clear(confirmPassword);
    }

    private char[] copyPassword(char[] value) {
        return value == null ? new char[0] : Arrays.copyOf(value, value.length);
    }

    private void clear(char[] value) {
        if (value != null) {
            Arrays.fill(value, '\0');
        }
    }
}