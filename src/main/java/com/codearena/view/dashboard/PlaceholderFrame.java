package com.codearena.view.dashboard;

import com.codearena.constants.ApplicationConstants;
import com.codearena.view.components.HeaderPanel;
import com.codearena.view.components.RoundedButton;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;

/**
 * Professional placeholder window for dashboard modules that will be implemented later.
 */
public class PlaceholderFrame extends JFrame {

    public PlaceholderFrame(String title, String message) {
        super(title + " - " + ApplicationConstants.APP_NAME);
        configureWindow(title, message);
    }

    private void configureWindow(String title, String message) {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(560, 360));
        setSize(680, 420);
        setLocationRelativeTo(null);

        JPanel rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBackground(ApplicationConstants.BACKGROUND_COLOR);
        rootPanel.setBorder(BorderFactory.createEmptyBorder(32, 40, 32, 40));

        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.add(new HeaderPanel(title, "Module placeholder"));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 28)));

        JLabel messageLabel = new JLabel("<html><div style='text-align:center;'>" + message + "</div></html>", SwingConstants.CENTER);
        messageLabel.setAlignmentX(CENTER_ALIGNMENT);
        messageLabel.setFont(ApplicationConstants.SUBTITLE_FONT);
        messageLabel.setForeground(ApplicationConstants.SECONDARY_TEXT_COLOR);
        contentPanel.add(messageLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 28)));

        RoundedButton closeButton = RoundedButton.primary("Close");
        closeButton.setAlignmentX(CENTER_ALIGNMENT);
        closeButton.setPreferredSize(new Dimension(120, 38));
        closeButton.addActionListener(event -> dispose());
        contentPanel.add(closeButton);

        rootPanel.add(contentPanel, BorderLayout.CENTER);
        setContentPane(rootPanel);
    }
}
