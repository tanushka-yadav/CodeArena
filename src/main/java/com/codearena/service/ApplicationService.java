package com.codearena.service;

import com.codearena.constants.AppConstants;
import com.codearena.model.AppInfo;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Provides application-level startup information.
 */

public class ApplicationService {

    public Optional<AppInfo> loadApplicationInfo() {
        AppInfo appInfo = new AppInfo(
                AppConstants.APP_NAME,
                AppConstants.APP_VERSION,
                LocalDate.of(2026, 8, 1)
        );
        return Optional.of(appInfo);
    }

}
