package com.codearena;


import com.codearena.controller.AppController;
import com.codearena.controller.NavigationController;
import com.codearena.controller.RegistrationController;
import com.codearena.dto.RegistrationRequest;
import com.codearena.dto.RegistrationResponse;
import com.codearena.enums.Gender;
import com.codearena.exception.ApplicationStartupException;
import com.codearena.model.AppInfo;
import com.codearena.repository.CandidateRepository;
import com.codearena.service.ApplicationService;
import com.codearena.service.RegistrationService;
import com.codearena.service.impl.RegistrationServiceImpl;
import com.codearena.validator.RegistrationValidator;
import com.codearena.view.MainFrame;
import com.codearena.view.registration.RegistrationFrame;

import java.awt.GraphicsEnvironment;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Application entry point for CodeArena.
 */
public final class CodeArenaApplication {

    private CodeArenaApplication() {
    }

    public static void main(String[] args) {
        if (isSmokeTest(args)) {
            runSmokeTest();
            return;
        }
        SwingUtilities.invokeLater(CodeArenaApplication::launch);
    }

    private static boolean isSmokeTest(String[] args) {
        if (args == null) {
            return false;
        }
        for (String arg : args) {
            if ("--smoke-test".equals(arg)) {
                return true;
            }
        }
        return false;
    }

    private static void launch() {
        try {
            AppController appController = createApplicationController();
            appController.start();
        } catch (ApplicationStartupException exception) {
            handleStartupFailure(exception.getMessage(), exception);
        } catch (RuntimeException exception) {
            handleStartupFailure("CodeArena could not start. Please check the application logs.", exception);
        }
    }

    private static AppController createApplicationController() {
        ApplicationService applicationService = new ApplicationService();
        MainFrame mainFrame = new MainFrame();
        RegistrationFrame registrationFrame = new RegistrationFrame();
        NavigationController navigationController = new NavigationController(mainFrame, registrationFrame);
        RegistrationService registrationService = createRegistrationService();

        createRegistrationController(registrationService, registrationFrame, navigationController);
        return new AppController(applicationService, mainFrame, navigationController);
    }

    private static RegistrationService createRegistrationService() {
        CandidateRepository candidateRepository = new CandidateRepository();
        RegistrationValidator registrationValidator = new RegistrationValidator();
        return new RegistrationServiceImpl(candidateRepository, registrationValidator);
    }

    private static RegistrationController createRegistrationController(RegistrationService registrationService,
                                                                       RegistrationFrame registrationFrame,
                                                                       NavigationController navigationController) {
        return new RegistrationController(
                registrationService,
                registrationFrame.getRegistrationPanel(),
                navigationController
        );
    }

    private static void runSmokeTest() {
        ApplicationService applicationService = new ApplicationService();
        AppInfo appInfo = applicationService.loadApplicationInfo()
                .orElseThrow(() -> new ApplicationStartupException("Unable to load CodeArena application metadata."));
        if (appInfo.getName().isBlank() || appInfo.getVersion().isBlank()) {
            throw new ApplicationStartupException("CodeArena application metadata is incomplete.");
        }

        RegistrationService registrationService = createRegistrationService();
        RegistrationRequest request = new RegistrationRequest(
                "Smoke Test User",
                "smoke_user",
                "smoke.user@example.com",
                "+15551234567",
                "SmokePass1!".toCharArray(),
                "SmokePass1!".toCharArray(),
                Gender.OTHER,
                "2000-01-01",
                LocalDate.of(2000, 1, 1)
        );
        RegistrationResponse response = registrationService.registerCandidate(request);
        if (!response.isSuccessful()) {
            throw new ApplicationStartupException("Registration smoke test failed: " + response.getErrors());
        }
        System.out.println("CodeArena smoke test completed successfully.");
    }

    private static void handleStartupFailure(String userMessage, RuntimeException exception) {
        System.err.println(userMessage);
        exception.printStackTrace(System.err);
        if (!GraphicsEnvironment.isHeadless()) {
            JOptionPane.showMessageDialog(null, userMessage, "Startup Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}