package com.codearena.view.components;

import com.codearena.constants.ApplicationConstants;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Reusable rounded button for primary and secondary actions.
 */
public class RoundedButton extends JButton {

    private final Color backgroundColor;
    private final Color hoverColor;

    public RoundedButton(String text, Color backgroundColor, Color hoverColor) {
        super(text);
        this.backgroundColor = backgroundColor;
        this.hoverColor = hoverColor;
        setFont(ApplicationConstants.LABEL_FONT);
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static RoundedButton primary(String text) {
        return new RoundedButton(text, ApplicationConstants.PRIMARY_COLOR, ApplicationConstants.PRIMARY_DARK_COLOR);
    }

    public static RoundedButton secondary(String text) {
        return new RoundedButton(text, new Color(71, 85, 105), new Color(51, 65, 85));
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics.create();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(getModel().isRollover() ? hoverColor : backgroundColor);
        graphics2D.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
        graphics2D.dispose();
        super.paintComponent(graphics);
    }
}
