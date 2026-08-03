package com.codearena.view.components;

import com.codearena.constants.ApplicationConstants;

import javax.swing.BorderFactory;
import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

/**
 * Rounded password field with consistent form styling.
 */
public class RoundedPasswordField extends JPasswordField {

    public RoundedPasswordField(int columns) {
        super(columns);
        setFont(ApplicationConstants.INPUT_FONT);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
    }

    @Override
    public Insets getInsets() {
        return new Insets(8, 12, 8, 12);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics.create();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
        super.paintComponent(graphics);
        graphics2D.dispose();
    }

    @Override
    protected void paintBorder(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics.create();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(ApplicationConstants.BORDER_COLOR);
        graphics2D.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
        graphics2D.dispose();
    }
}
