package com.codearena.service;

import com.codearena.dto.DashboardSummary;

/**
 * Dashboard use-case boundary for candidate workspace data.
 */
public interface DashboardService {

    DashboardSummary loadDashboardSummary();
}
