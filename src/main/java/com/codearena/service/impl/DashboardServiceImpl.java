package com.codearena.service.impl;

import com.codearena.constants.ApplicationConstants;
import com.codearena.dto.DashboardSummary;
import com.codearena.model.Candidate;
import com.codearena.service.DashboardService;
import com.codearena.service.SessionManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Builds dashboard data from the active candidate session.
 */
public class DashboardServiceImpl implements DashboardService {

    private final SessionManager sessionManager;

    public DashboardServiceImpl(SessionManager sessionManager) {
        this.sessionManager = Objects.requireNonNull(sessionManager, "sessionManager is required");
    }

    @Override
    public DashboardSummary loadDashboardSummary() {
        Candidate candidate = sessionManager.getCurrentCandidate()
                .orElseThrow(() -> new IllegalStateException("No candidate session is active."));

        return new DashboardSummary(
                candidate,
                LocalDate.now(),
                LocalTime.now(),
                sessionManager.getLoginTime()
                        .orElseThrow(() -> new IllegalStateException("Candidate login time is unavailable.")),
                sessionManager.isAuthenticated(),
                ApplicationConstants.APP_VERSION,
                sessionManager.getSessionId()
        );
    }
}
