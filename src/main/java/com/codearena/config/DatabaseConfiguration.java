package com.codearena.config;

/**
 * Centralized database configuration loaded from system properties or environment variables.
 */
public class DatabaseConfiguration {

    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/codearena?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DEFAULT_USERNAME = "root";
    private static final String DEFAULT_PASSWORD = "mysql@2006";
    private static final String DEFAULT_DRIVER = "com.mysql.cj.jdbc.Driver";

    private final String url;
    private final String username;
    private final String password;
    private final String driverClassName;

    public DatabaseConfiguration() {
        this(
                readSetting("codearena.db.url", "CODEARENA_DB_URL", DEFAULT_URL),
                readSetting("codearena.db.username", "CODEARENA_DB_USERNAME", DEFAULT_USERNAME),
                readSetting("codearena.db.password", "CODEARENA_DB_PASSWORD", DEFAULT_PASSWORD),
                readSetting("codearena.db.driver", "CODEARENA_DB_DRIVER", DEFAULT_DRIVER)
        );
    }

    public DatabaseConfiguration(String url, String username, String password, String driverClassName) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    private static String readSetting(String propertyName, String environmentName, String defaultValue) {
        String propertyValue = System.getProperty(propertyName);
        if (propertyValue != null && !propertyValue.isBlank()) {
            return propertyValue;
        }

        String environmentValue = System.getenv(environmentName);
        return environmentValue == null || environmentValue.isBlank() ? defaultValue : environmentValue;
    }
}
