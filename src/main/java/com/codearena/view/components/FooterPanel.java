package com.codearena.view.components;
import com.codearena.constants.ApplicationConstants;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;

/**
 * Reusable footer for application screens.
 */
public class FooterPanel extends JPanel {

    public FooterPanel() {
        setOpaque(false);
        setLayout(new BorderLayout());

        JLabel footerLabel = new JLabel("CodeArena " + ApplicationConstants.APP_VERSION, SwingConstants.CENTER);
        footerLabel.setFont(ApplicationConstants.SUBTITLE_FONT);
        footerLabel.setForeground(ApplicationConstants.SECONDARY_TEXT_COLOR);
        add(footerLabel, BorderLayout.CENTER);
    }
}