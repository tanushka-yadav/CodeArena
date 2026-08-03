package com.codearena.view;

import com.codearena.constants.AppConstants;
import com.codearena.constants.ApplicationConstants;
import com.codearena.model.AppInfo;
import com.codearena.util.DateTimeUtil;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

/**
 * Main desktop window for CodeArena.
 */
public class MainFrame extends JFrame {

    private final JLabel titleLabel;
    private final JLabel subtitleLabel;
    private final JLabel versionLabel;
    private final JButton registrationButton;
    private final JPanel welcomePanel;

    public MainFrame() {
        super(AppConstants.APP_NAME);
        this.titleLabel = new JLabel("", SwingConstants.CENTER);
        this.subtitleLabel = new JLabel("Internship-ready coding assessments with Java Swing", SwingConstants.CENTER);
        this.versionLabel = new JLabel("", SwingConstants.CENTER);
        this.registrationButton = new JButton("Open Candidate Registration");
        this.welcomePanel = buildWelcomePanel();

        configureWindow();
        setContentPane(welcomePanel);
    }

    public void render(AppInfo appInfo) {
        titleLabel.setText(appInfo.getName());
        versionLabel.setText("Version " + appInfo.getVersion()
                + " | Released " + DateTimeUtil.formatDate(appInfo.getReleaseDate()));
    }

    public void onOpenRegistration(ActionListener actionListener) {
        registrationButton.addActionListener(actionListener);
    }

    public void showWelcomePanel() {
        showScreen(welcomePanel);
    }

    public void showScreen(JComponent screen) {
        setContentPane(screen);
        revalidate();
        repaint();
    }

    private void configureWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(900, 600));
        setSize(AppConstants.WINDOW_WIDTH, AppConstants.WINDOW_HEIGHT);
        setLocationRelativeTo(null);
    }

    private JPanel buildWelcomePanel() {
        JPanel rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBackground(ApplicationConstants.BACKGROUND_COLOR);
        rootPanel.setBorder(BorderFactory.createEmptyBorder(40, 56, 40, 56));

        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        titleLabel.setFont(ApplicationConstants.TITLE_FONT);
        titleLabel.setForeground(ApplicationConstants.HEADING_TEXT_COLOR);

        subtitleLabel.setAlignmentX(CENTER_ALIGNMENT);
        subtitleLabel.setFont(ApplicationConstants.SUBTITLE_FONT);
        subtitleLabel.setForeground(ApplicationConstants.SECONDARY_TEXT_COLOR);

        registrationButton.setAlignmentX(CENTER_ALIGNMENT);
        registrationButton.setFont(ApplicationConstants.LABEL_FONT);

        versionLabel.setAlignmentX(CENTER_ALIGNMENT);
        versionLabel.setFont(ApplicationConstants.SUBTITLE_FONT);
        versionLabel.setForeground(ApplicationConstants.SECONDARY_TEXT_COLOR);

        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 14)));
        contentPanel.add(subtitleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 28)));
        contentPanel.add(registrationButton);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 18)));
        contentPanel.add(versionLabel);
        contentPanel.add(Box.createVerticalGlue());

        rootPanel.add(contentPanel, BorderLayout.CENTER);
        return rootPanel;
    }
}
