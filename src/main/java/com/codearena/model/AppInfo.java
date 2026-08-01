package com.codearena.model;

import java.time.LocalDate;

/**
 * Immutable application metadata displayed during startup.
 */

public class AppInfo {

    private final String name;
    private final String version;
    private final LocalDate releaseDate;

    public AppInfo(String name, String version, LocalDate releaseDate) {
        this.name = name;
        this.version = version;
        this.releaseDate = releaseDate;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

}
