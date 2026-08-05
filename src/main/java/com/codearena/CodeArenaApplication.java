package com.codearena;

import com.codearena.controller.AppController;
import com.codearena.controller.AuthenticationController;
import com.codearena.controller.DashboardController;
import com.codearena.controller.NavigationController;
import com.codearena.controller.RegistrationController;
import com.codearena.config.DatabaseConfiguration;
import com.codearena.database.ConnectionFactory;
import com.codearena.database.DatabaseInitializer;
import com.codearena.dto.AuthenticationResult;
import com.codearena.dto.LoginRequest;
import com.codearena.dto.RegistrationRequest;
import com.codearena.dto.RegistrationResponse;
import com.codearena.enums.Gender;
import com.codearena.exception.ApplicationStartupException;
import com.codearena.interfaces.PasswordEncoder;
import com.codearena.model.AppInfo;
import com.codearena.repository.CandidateRepository;
import com.codearena.repository.JdbcCandidateRepository;
import com.codearena.service.ApplicationService;
import com.codearena.service.AuthenticationService;
import com.codearena.service.DashboardService;
import com.codearena.service.RegistrationService;
import com.codearena.service.SessionManager;
import com.codearena.service.impl.AuthenticationServiceImpl;
import com.codearena.service.impl.DashboardServiceImpl;
import com.codearena.service.impl.RegistrationServiceImpl;
import com.codearena.util.Pbkdf2PasswordEncoder;
import com.codearena.validator.CredentialValidator;
import com.codearena.validator.RegistrationValidator;
import com.codearena.view.MainFrame;
import com.codearena.view.authentication.LoginFrame;
import com.codearena.view.dashboard.DashboardPanel;
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
        LoginFrame loginFrame = new LoginFrame();
        RegistrationFrame registrationFrame = new RegistrationFrame();
        DashboardPanel dashboardPanel = new DashboardPanel();
        NavigationController navigationController = new NavigationController(mainFrame, loginFrame, registrationFrame);
        CandidateRepository candidateRepository = createCandidateRepository();
        PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder();
        SessionManager sessionManager = new SessionManager();
        RegistrationService registrationService = createRegistrationService(candidateRepository, passwordEncoder);
        AuthenticationService authenticationService = createAuthenticationService(
                candidateRepository,
                passwordEncoder,
                sessionManager
        );
        DashboardService dashboardService = new DashboardServiceImpl(sessionManager);

        createRegistrationController(registrationService, registrationFrame, navigationController);
        DashboardController dashboardController = createDashboardController(
                dashboardService,
                sessionManager,
                dashboardPanel,
                navigationController
        );
        createAuthenticationController(authenticationService, loginFrame, navigationController, dashboardController);
        navigationController.setDashboardPanel(dashboardPanel);
        return new AppController(applicationService, mainFrame, navigationController);
    }

    private static RegistrationService createRegistrationService(CandidateRepository candidateRepository,
                                                                 PasswordEncoder passwordEncoder) {
        RegistrationValidator registrationValidator = new RegistrationValidator();
        return new RegistrationServiceImpl(candidateRepository, registrationValidator, passwordEncoder);
    }

    private static AuthenticationService createAuthenticationService(CandidateRepository candidateRepository,
                                                                     PasswordEncoder passwordEncoder,
                                                                     SessionManager sessionManager) {
        return new AuthenticationServiceImpl(
                candidateRepository,
                new CredentialValidator(),
                passwordEncoder,
                sessionManager
        );
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

    private static AuthenticationController createAuthenticationController(AuthenticationService authenticationService,
                                                                           LoginFrame loginFrame,
                                                                           NavigationController navigationController,
                                                                           DashboardController dashboardController) {
        AuthenticationController authenticationController = new AuthenticationController(
                authenticationService,
                loginFrame.getLoginPanel(),
                navigationController
        );
        authenticationController.setDashboardController(dashboardController);
        return authenticationController;
    }

    private static DashboardController createDashboardController(DashboardService dashboardService,
                                                                 SessionManager sessionManager,
                                                                 DashboardPanel dashboardPanel,
                                                                 NavigationController navigationController) {
        return new DashboardController(dashboardService, sessionManager, dashboardPanel, navigationController);
    }

    private static void runSmokeTest() {
        ApplicationService applicationService = new ApplicationService();
        AppInfo appInfo = applicationService.loadApplicationInfo()
                .orElseThrow(() -> new ApplicationStartupException("Unable to load CodeArena application metadata."));
        if (appInfo.getName().isBlank() || appInfo.getVersion().isBlank()) {
            throw new ApplicationStartupException("CodeArena application metadata is incomplete.");
        }

        CandidateRepository candidateRepository = createCandidateRepository();
        PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder();
        SessionManager sessionManager = new SessionManager();
        RegistrationService registrationService = createRegistrationService(candidateRepository, passwordEncoder);
        AuthenticationService authenticationService = createAuthenticationService(
                candidateRepository,
                passwordEncoder,
                sessionManager
        );
        DashboardService dashboardService = new DashboardServiceImpl(sessionManager);
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
        RegistrationResponse duplicateResponse = registrationService.registerCandidate(new RegistrationRequest(
                "Smoke Test User",
                "smoke_user",
                "smoke.user@example.com",
                "+15551234567",
                "SmokePass1!".toCharArray(),
                "SmokePass1!".toCharArray(),
                Gender.OTHER,
                "2000-01-01",
                LocalDate.of(2000, 1, 1)
        ));
        if (duplicateResponse.isSuccessful()) {
            throw new ApplicationStartupException("Duplicate registration smoke test failed.");
        }
        AuthenticationResult authenticationResult = authenticationService.authenticate(new LoginRequest(
                "smoke.user@example.com",
                "SmokePass1!".toCharArray(),
                false
        ));
        if (!authenticationResult.isAuthenticated() || !sessionManager.isAuthenticated()) {
            throw new ApplicationStartupException("Authentication smoke test failed: " + authenticationResult.getErrors());
        }
        if (dashboardService.loadDashboardSummary().getCandidateName().isBlank()) {
            throw new ApplicationStartupException("Dashboard smoke test failed: candidate name is missing.");
        }
        sessionManager.logout();
        if (sessionManager.isAuthenticated()) {
            throw new ApplicationStartupException("Logout smoke test failed.");
        }
        AuthenticationResult usernameAuthenticationResult = authenticationService.authenticate(new LoginRequest(
                "smoke_user",
                "SmokePass1!".toCharArray(),
                false
        ));
        if (!usernameAuthenticationResult.isAuthenticated() || !sessionManager.isAuthenticated()) {
            throw new ApplicationStartupException("Username login smoke test failed: "
                    + usernameAuthenticationResult.getErrors());
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

    private static CandidateRepository createCandidateRepository() {
        DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();
        ConnectionFactory connectionFactory = new ConnectionFactory(databaseConfiguration);
        new DatabaseInitializer(connectionFactory).initialize();
        return new JdbcCandidateRepository(connectionFactory);
    }
}
