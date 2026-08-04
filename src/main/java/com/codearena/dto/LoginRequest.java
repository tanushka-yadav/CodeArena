package com.codearena.dto;

import java.util.Arrays;

/**
 * Carries candidate login values from the Swing view to the authentication service.
 */
public class LoginRequest {

    private String usernameOrEmail;
    private char[] password;
    private boolean rememberMe;

    public LoginRequest(String usernameOrEmail, char[] password, boolean rememberMe) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = copyPassword(password);
        this.rememberMe = rememberMe;
    }

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public char[] getPassword() {
        return copyPassword(password);
    }

    public void setPassword(char[] password) {
        clear(this.password);
        this.password = copyPassword(password);
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public void clearSensitiveData() {
        clear(password);
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
