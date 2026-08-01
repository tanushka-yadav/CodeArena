package com.codearena.controller;

import com.codearena.exception.ApplicationStartupException;
import com.codearena.model.AppInfo;
import com.codearena.service.ApplicationService;
import com.codearena.view.MainFrame;

import java.util.Objects;

/**
 * Coordinates startup data between the model/service layer and Swing view.
 */

public class AppController {

    private final ApplicationService applicationService;
    private final MainFrame mainFrame;

    public AppController(ApplicationService applicationService, MainFrame mainFrame) {
        this.applicationService = Objects.requireNonNull(applicationService, "applicationService is required");
        this.mainFrame = Objects.requireNonNull(mainFrame, "mainFrame is required");
    }

    public void start() {
        AppInfo appInfo = applicationService.loadApplicationInfo()
                .orElseThrow(() -> new ApplicationStartupException("Unable to load CodeArena application metadata."));

        mainFrame.render(appInfo);
        mainFrame.setVisible(true);
    }

}
