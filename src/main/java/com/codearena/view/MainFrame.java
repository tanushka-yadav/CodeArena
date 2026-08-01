package com.codearena.view;

import com.codearena.constants.AppConstants;
import com.codearena.model.AppInfo;
import com.codearena.util.DateTimeUtil;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

/**
 * Main desktop window for CodeArena.
 */

public class MainFrame extends JFrame{

    private final JLabel titleLabel;
    private final JLabel subtitleLabel;
    private final JLabel versionLabel;

    public MainFrame() {
        super(AppConstants.APP_NAME);
        this.titleLabel = new JLabel("", SwingConstants.CENTER);
        this.subtitleLabel = new JLabel("Internship-ready coding assessments with Java Swing", SwingConstants.CENTER);
        this.versionLabel = new JLabel("", SwingConstants.CENTER);

        configureWindow();
        buildLayout();
    }

    public void render(AppInfo appInfo) {
        titleLabel.setText(appInfo.getName());
        versionLabel.setText("Version " + appInfo.getVersion()
                + " | Released " + DateTimeUtil.formatDate(appInfo.getReleaseDate()));
    }

    private void configureWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(900, 600));
        setSize(AppConstants.WINDOW_WIDTH, AppConstants.WINDOW_HEIGHT);
        setLocationRelativeTo(null);
    }

    private void buildLayout() {
        JPanel rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBackground(new Color(246, 248, 251));
        rootPanel.setBorder(BorderFactory.createEmptyBorder(40, 56, 40, 56));

        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 34));
        titleLabel.setForeground(new Color(24, 33, 46));

        subtitleLabel.setAlignmentX(CENTER_ALIGNMENT);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitleLabel.setForeground(new Color(82, 95, 113));

        JButton startButton = new JButton("Project Foundation Ready");
        startButton.setAlignmentX(CENTER_ALIGNMENT);
        startButton.setEnabled(false);
        startButton.setFont(new Font("Segoe UI", Font.BOLD, 15));

        versionLabel.setAlignmentX(CENTER_ALIGNMENT);
        versionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        versionLabel.setForeground(new Color(102, 116, 134));

        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 14)));
        contentPanel.add(subtitleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 28)));
        contentPanel.add(startButton);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 18)));
        contentPanel.add(versionLabel);
        contentPanel.add(Box.createVerticalGlue());

        rootPanel.add(contentPanel, BorderLayout.CENTER);
        setContentPane(rootPanel);
    }

}
