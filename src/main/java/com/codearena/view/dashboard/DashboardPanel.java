package com.codearena.view.dashboard;

import com.codearena.constants.ApplicationConstants;
import com.codearena.dto.DashboardSummary;
import com.codearena.util.DateTimeUtil;
import com.codearena.view.components.RoundedButton;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;

/**
 * Candidate dashboard view with status information and quick actions.
 */
public class DashboardPanel extends JPanel {

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("hh:mm a");
    private static final DateTimeFormatter SESSION_TIME_FORMAT = DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a");

    private final JLabel welcomeLabel;
    private final JLabel usernameLabel;
    private final JLabel dateLabel;
    private final JLabel timeLabel;
    private final JLabel sessionLabel;
    private final JLabel loginStatusLabel;
    private final JLabel versionLabel;
    private final RoundedButton startTestButton;
    private final RoundedButton resultsButton;
    private final RoundedButton leaderboardButton;
    private final RoundedButton profileButton;
    private final RoundedButton settingsButton;
    private final RoundedButton helpButton;
    private final RoundedButton logoutButton;

    public DashboardPanel() {
        this.welcomeLabel = new JLabel("", SwingConstants.LEFT);
        this.usernameLabel = new JLabel("", SwingConstants.LEFT);
        this.dateLabel = new JLabel("", SwingConstants.LEFT);
        this.timeLabel = new JLabel("", SwingConstants.LEFT);
        this.sessionLabel = new JLabel("", SwingConstants.LEFT);
        this.loginStatusLabel = new JLabel("", SwingConstants.LEFT);
        this.versionLabel = new JLabel("", SwingConstants.LEFT);
        this.startTestButton = RoundedButton.primary("Start Coding Test");
        this.resultsButton = RoundedButton.secondary("View Previous Results");
        this.leaderboardButton = RoundedButton.secondary("Leaderboard");
        this.profileButton = RoundedButton.secondary("My Profile");
        this.settingsButton = RoundedButton.secondary("Settings");
        this.helpButton = RoundedButton.secondary("Help");
        this.logoutButton = RoundedButton.secondary("Logout");

        buildLayout();
    }

    public void render(DashboardSummary summary) {
        welcomeLabel.setText("Welcome, " + summary.getCandidateName());
        usernameLabel.setText("Username: " + summary.getUsername());
        dateLabel.setText("Today's Date: " + DateTimeUtil.formatDate(summary.getCurrentDate()));
        timeLabel.setText("Current Time: " + summary.getCurrentTime().format(TIME_FORMAT));
        sessionLabel.setText("Current Session: " + summary.getLoginTime().format(SESSION_TIME_FORMAT));
        loginStatusLabel.setText("Login Status: " + (summary.isAuthenticated() ? "Authenticated" : "Signed out"));
        versionLabel.setText("Application Version: " + summary.getApplicationVersion());
    }

    public void onStartTest(ActionListener listener) {
        startTestButton.addActionListener(listener);
    }

    public void onResults(ActionListener listener) {
        resultsButton.addActionListener(listener);
    }

    public void onLeaderboard(ActionListener listener) {
        leaderboardButton.addActionListener(listener);
    }

    public void onProfile(ActionListener listener) {
        profileButton.addActionListener(listener);
    }

    public void onSettings(ActionListener listener) {
        settingsButton.addActionListener(listener);
    }

    public void onHelp(ActionListener listener) {
        helpButton.addActionListener(listener);
    }

    public void onLogout(ActionListener listener) {
        logoutButton.addActionListener(listener);
    }

    private void buildLayout() {
        setLayout(new BorderLayout(24, 24));
        setBackground(ApplicationConstants.BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(28, 36, 28, 36));

        add(buildBannerPanel(), BorderLayout.NORTH);
        add(buildCenterPanel(), BorderLayout.CENTER);
        add(buildFooterActions(), BorderLayout.SOUTH);
    }

    private JPanel buildBannerPanel() {
        JPanel banner = new JPanel(new BorderLayout());
        banner.setOpaque(false);
        banner.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));

        welcomeLabel.setFont(ApplicationConstants.TITLE_FONT);
        welcomeLabel.setForeground(ApplicationConstants.HEADING_TEXT_COLOR);
        usernameLabel.setFont(ApplicationConstants.SUBTITLE_FONT);
        usernameLabel.setForeground(ApplicationConstants.SECONDARY_TEXT_COLOR);

        JPanel titleStack = new JPanel();
        titleStack.setOpaque(false);
        titleStack.setLayout(new BoxLayout(titleStack, BoxLayout.Y_AXIS));
        titleStack.add(welcomeLabel);
        titleStack.add(Box.createRigidArea(new Dimension(0, 8)));
        titleStack.add(usernameLabel);

        banner.add(titleStack, BorderLayout.CENTER);
        return banner;
    }

    private JPanel buildCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout(24, 24));
        centerPanel.setOpaque(false);
        centerPanel.add(buildStatusPanel(), BorderLayout.NORTH);
        centerPanel.add(buildQuickActionsPanel(), BorderLayout.CENTER);
        return centerPanel;
    }

    private JPanel buildStatusPanel() {
        JPanel statusPanel = new JPanel(new GridLayout(2, 3, 16, 12));
        statusPanel.setOpaque(false);
        statusPanel.add(statusItem(dateLabel));
        statusPanel.add(statusItem(timeLabel));
        statusPanel.add(statusItem(sessionLabel));
        statusPanel.add(statusItem(loginStatusLabel));
        statusPanel.add(statusItem(versionLabel));
        statusPanel.add(statusItem(new JLabel("Workspace: Candidate", SwingConstants.LEFT)));
        return statusPanel;
    }

    private JPanel buildQuickActionsPanel() {
        JPanel actionsPanel = new JPanel(new GridLayout(2, 3, 18, 18));
        actionsPanel.setOpaque(false);
        actionsPanel.add(actionCard("Available Coding Tests", "Browse assigned coding assessments.", startTestButton));
        actionsPanel.add(actionCard("My Test History", "Review previous submissions and scores.", resultsButton));
        actionsPanel.add(actionCard("Leaderboard", "Compare performance with peers.", leaderboardButton));
        actionsPanel.add(actionCard("Profile", "View candidate account details.", profileButton));
        actionsPanel.add(actionCard("Settings", "Adjust candidate workspace preferences.", settingsButton));
        actionsPanel.add(actionCard("Help", "Get guidance for using CodeArena.", helpButton));
        return actionsPanel;
    }

    private JPanel buildFooterActions() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        footer.setOpaque(false);
        logoutButton.setPreferredSize(new Dimension(120, 40));
        footer.add(logoutButton);
        return footer;
    }

    private JPanel actionCard(String title, String description, RoundedButton button) {
        JPanel card = new JPanel(new BorderLayout(10, 12));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ApplicationConstants.BORDER_COLOR),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(ApplicationConstants.LABEL_FONT);
        titleLabel.setForeground(ApplicationConstants.HEADING_TEXT_COLOR);

        JLabel descriptionLabel = new JLabel("<html>" + description + "</html>");
        descriptionLabel.setFont(ApplicationConstants.SUBTITLE_FONT);
        descriptionLabel.setForeground(ApplicationConstants.SECONDARY_TEXT_COLOR);

        button.setPreferredSize(new Dimension(180, 38));

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.add(titleLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        textPanel.add(descriptionLabel);

        card.add(textPanel, BorderLayout.CENTER);
        card.add(button, BorderLayout.SOUTH);
        return card;
    }

    private JPanel statusItem(JLabel label) {
        JPanel item = new JPanel(new BorderLayout());
        item.setBackground(Color.WHITE);
        item.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ApplicationConstants.BORDER_COLOR),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        label.setFont(ApplicationConstants.SUBTITLE_FONT);
        label.setForeground(ApplicationConstants.HEADING_TEXT_COLOR);
        item.add(label, BorderLayout.CENTER);
        return item;
    }
}
