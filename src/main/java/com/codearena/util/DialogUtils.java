package com.codearena.util;
import javax.swing.JOptionPane;
import java.awt.Component;
import java.util.List;

/**
 * Centralizes user-facing Swing dialogs.
 */
public final class DialogUtils {

    private DialogUtils() {
    }

    public static void showSuccess(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showValidationErrors(Component parent, List<String> errors) {
        StringBuilder message = new StringBuilder("Please fix the following issues:\n\n");
        for (String error : errors) {
            message.append("- ").append(error).append('\n');
        }
        JOptionPane.showMessageDialog(parent, message.toString(), "Validation Error", JOptionPane.WARNING_MESSAGE);
    }
}
