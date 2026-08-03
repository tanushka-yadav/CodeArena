package com.codearena.view.components;

import com.codearena.constants.ApplicationConstants;

import javax.swing.JLabel;

/**
 * Consistent label used above registration inputs.
 */
public class FormLabel extends JLabel {

    public FormLabel(String text) {
        super(text);
        setFont(ApplicationConstants.LABEL_FONT);
        setForeground(ApplicationConstants.HEADING_TEXT_COLOR);
    }
}