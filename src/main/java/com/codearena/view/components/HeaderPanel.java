package com.codearena.view.components;

import com.codearena.constants.ApplicationConstants;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Dimension;

/**
 * Reusable centered page header.
 */
public class HeaderPanel extends JPanel {

    public HeaderPanel(String title, String subtitle) {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        titleLabel.setFont(ApplicationConstants.TITLE_FONT);
        titleLabel.setForeground(ApplicationConstants.HEADING_TEXT_COLOR);

        JLabel subtitleLabel = new JLabel(subtitle, SwingConstants.CENTER);
        subtitleLabel.setAlignmentX(CENTER_ALIGNMENT);
        subtitleLabel.setFont(ApplicationConstants.SUBTITLE_FONT);
        subtitleLabel.setForeground(ApplicationConstants.SECONDARY_TEXT_COLOR);

        add(titleLabel);
        add(javax.swing.Box.createRigidArea(new Dimension(0, 8)));
        add(subtitleLabel);
    }
}
