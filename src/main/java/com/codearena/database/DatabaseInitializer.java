package com.codearena.database;

import com.codearena.exception.DatabaseException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

/**
 * Creates required database tables and indexes when the application starts.
 */
public class DatabaseInitializer {

    private static final String CREATE_CANDIDATE_TABLE = """
            CREATE TABLE IF NOT EXISTS candidate (
                candidate_id VARCHAR(36) PRIMARY KEY,
                full_name VARCHAR(100) NOT NULL,
                username VARCHAR(50) NOT NULL,
                email VARCHAR(150) NOT NULL,
                mobile_number VARCHAR(20) NOT NULL,
                password_hash VARCHAR(255) NOT NULL,
                gender VARCHAR(30) NOT NULL,
                date_of_birth DATE NOT NULL,
                created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                active BOOLEAN NOT NULL DEFAULT TRUE,
                CONSTRAINT uk_candidate_username UNIQUE (username),
                CONSTRAINT uk_candidate_email UNIQUE (email)
            )
            """;

    private static final String CREATE_USERNAME_INDEX = "CREATE INDEX idx_candidate_username ON candidate (username)";
    private static final String CREATE_EMAIL_INDEX = "CREATE INDEX idx_candidate_email ON candidate (email)";

    private final ConnectionFactory connectionFactory;

    public DatabaseInitializer(ConnectionFactory connectionFactory) {
        this.connectionFactory = Objects.requireNonNull(connectionFactory, "connectionFactory is required");
    }

    public void initialize() {
        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(CREATE_CANDIDATE_TABLE);
            createIndexIfMissing(statement, CREATE_USERNAME_INDEX);
            createIndexIfMissing(statement, CREATE_EMAIL_INDEX);
        } catch (SQLException exception) {
            throw new DatabaseException("Unable to initialize CodeArena database schema.", exception);
        }
    }

    private void createIndexIfMissing(Statement statement, String sql) throws SQLException {
        try {
            statement.execute(sql);
        } catch (SQLException exception) {
            if (!isDuplicateIndexError(exception)) {
                throw exception;
            }
        }
    }

    private boolean isDuplicateIndexError(SQLException exception) {
        return exception.getErrorCode() == 1061;
    }
}
