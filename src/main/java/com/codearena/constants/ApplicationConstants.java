package com.codearena.constants;

import java.awt.Color;
import java.awt.Font;

/**
 * Application-wide constants for UI branding and window behavior.
 */
public final class ApplicationConstants {

    public static final String APP_NAME = "CodeArena - Coding Test Platform";
    public static final String APP_VERSION = "0.6.0";
    public static final int WINDOW_WIDTH = 1100;
    public static final int WINDOW_HEIGHT = 720;

    public static final String FONT_FAMILY = "Segoe UI";
    public static final Font TITLE_FONT = new Font(FONT_FAMILY, Font.BOLD, 30);
    public static final Font SUBTITLE_FONT = new Font(FONT_FAMILY, Font.PLAIN, 15);
    public static final Font LABEL_FONT = new Font(FONT_FAMILY, Font.BOLD, 13);
    public static final Font INPUT_FONT = new Font(FONT_FAMILY, Font.PLAIN, 14);

    public static final Color BACKGROUND_COLOR = new Color(246, 248, 251);
    public static final Color PANEL_COLOR = Color.WHITE;
    public static final Color PRIMARY_COLOR = new Color(37, 99, 235);
    public static final Color PRIMARY_DARK_COLOR = new Color(29, 78, 216);
    public static final Color SECONDARY_TEXT_COLOR = new Color(82, 95, 113);
    public static final Color HEADING_TEXT_COLOR = new Color(24, 33, 46);
    public static final Color BORDER_COLOR = new Color(210, 218, 230);
    public static final Color ERROR_COLOR = new Color(185, 28, 28);

    private ApplicationConstants() {
    }
}
