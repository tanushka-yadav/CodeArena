package com.codearena.database;

import com.codearena.config.DatabaseConfiguration;
import com.codearena.exception.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Creates JDBC connections for repositories.
 */
public class ConnectionFactory {

    private final DatabaseConfiguration databaseConfiguration;

    public ConnectionFactory(DatabaseConfiguration databaseConfiguration) {
        this.databaseConfiguration = Objects.requireNonNull(databaseConfiguration, "databaseConfiguration is required");
        loadDriver();
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    databaseConfiguration.getUrl(),
                    databaseConfiguration.getUsername(),
                    databaseConfiguration.getPassword()
            );
        } catch (SQLException exception) {
            throw new DatabaseException("Unable to connect to the CodeArena database.", exception);
        }
    }

    private void loadDriver() {
        try {
            Class.forName(databaseConfiguration.getDriverClassName());
        } catch (ClassNotFoundException exception) {
            throw new DatabaseException("MySQL JDBC driver is not available on the application classpath.", exception);
        }
    }
}
