package com.codearena;

import com.codearena.controller.AppController;
import com.codearena.exception.ApplicationStartupException;
import com.codearena.service.ApplicationService;
import com.codearena.view.MainFrame;

import javax.swing.SwingUtilities;

/**
 * Application entry point for CodeArena.
 */
public final class CodeArenaApplication {

    private CodeArenaApplication() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                ApplicationService applicationService = new ApplicationService();
                MainFrame mainFrame = new MainFrame();
                AppController appController = new AppController(applicationService, mainFrame);
                appController.start();
            } catch (ApplicationStartupException exception) {
                System.err.println(exception.getMessage());
            }
        });
    }
}
