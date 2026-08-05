package com.codearena.repository;

import com.codearena.database.ConnectionFactory;
import com.codearena.enums.Gender;
import com.codearena.exception.DatabaseException;
import com.codearena.model.Candidate;
import com.codearena.model.CandidateProfile;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * JDBC-backed candidate repository for MySQL persistence.
 */
public class JdbcCandidateRepository implements CandidateRepository {

    private static final String INSERT_CANDIDATE = """
            INSERT INTO candidate (
                candidate_id,
                full_name,
                username,
                email,
                mobile_number,
                password_hash,
                gender,
                date_of_birth,
                created_at,
                updated_at,
                active
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, TRUE)
            """;

    private static final String EXISTS_BY_USERNAME = "SELECT 1 FROM candidate WHERE username = ? AND active = TRUE LIMIT 1";
    private static final String EXISTS_BY_EMAIL = "SELECT 1 FROM candidate WHERE email = ? AND active = TRUE LIMIT 1";
    private static final String FIND_BY_USERNAME = "SELECT * FROM candidate WHERE username = ? AND active = TRUE LIMIT 1";
    private static final String FIND_BY_EMAIL = "SELECT * FROM candidate WHERE email = ? AND active = TRUE LIMIT 1";
    private static final String FIND_ALL = "SELECT * FROM candidate WHERE active = TRUE ORDER BY created_at DESC";

    private final ConnectionFactory connectionFactory;

    public JdbcCandidateRepository(ConnectionFactory connectionFactory) {
        this.connectionFactory = Objects.requireNonNull(connectionFactory, "connectionFactory is required");
    }

    @Override
    public boolean saveIfUnique(Candidate candidate) {
        Objects.requireNonNull(candidate, "candidate is required");
        try (Connection connection = connectionFactory.getConnection()) {
            boolean originalAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            try {
                if (existsByUsername(connection, candidate.getUsername())
                        || existsByEmailAddress(connection, candidate.getEmailAddress())) {
                    connection.rollback();
                    return false;
                }
                insertCandidate(connection, candidate);
                connection.commit();
                return true;
            } catch (SQLIntegrityConstraintViolationException exception) {
                connection.rollback();
                return false;
            } catch (SQLException exception) {
                connection.rollback();
                throw exception;
            } finally {
                connection.setAutoCommit(originalAutoCommit);
            }
        } catch (SQLException exception) {
            throw new DatabaseException("Unable to save candidate registration.", exception);
        }
    }

    @Override
    public Candidate save(Candidate candidate) {
        if (!saveIfUnique(candidate)) {
            throw new IllegalArgumentException("Candidate username or email address already exists.");
        }
        return candidate;
    }

    @Override
    public boolean existsByUsername(String username) {
        try (Connection connection = connectionFactory.getConnection()) {
            return existsByUsername(connection, username);
        } catch (SQLException exception) {
            throw new DatabaseException("Unable to check candidate username availability.", exception);
        }
    }

    @Override
    public boolean existsByEmailAddress(String emailAddress) {
        try (Connection connection = connectionFactory.getConnection()) {
            return existsByEmailAddress(connection, emailAddress);
        } catch (SQLException exception) {
            throw new DatabaseException("Unable to check candidate email availability.", exception);
        }
    }

    @Override
    public Optional<Candidate> findByUsername(String username) {
        return findOne(FIND_BY_USERNAME, normalizeKey(username));
    }

    @Override
    public Optional<Candidate> findByEmailAddress(String emailAddress) {
        return findOne(FIND_BY_EMAIL, normalizeKey(emailAddress));
    }

    @Override
    public Optional<Candidate> findByUsernameOrEmail(String usernameOrEmail) {
        Optional<Candidate> candidate = findByUsername(usernameOrEmail);
        return candidate.isPresent() ? candidate : findByEmailAddress(usernameOrEmail);
    }

    @Override
    public List<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                candidates.add(mapCandidate(resultSet));
            }
            return candidates;
        } catch (SQLException exception) {
            throw new DatabaseException("Unable to load candidates.", exception);
        }
    }

    private boolean existsByUsername(Connection connection, String username) throws SQLException {
        return exists(connection, EXISTS_BY_USERNAME, normalizeKey(username));
    }

    private boolean existsByEmailAddress(Connection connection, String emailAddress) throws SQLException {
        return exists(connection, EXISTS_BY_EMAIL, normalizeKey(emailAddress));
    }

    private boolean exists(Connection connection, String sql, String value) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, value);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private void insertCandidate(Connection connection, Candidate candidate) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_CANDIDATE)) {
            CandidateProfile profile = candidate.getProfile();
            statement.setString(1, candidate.getCandidateId());
            statement.setString(2, profile.getFullName());
            statement.setString(3, normalizeKey(candidate.getUsername()));
            statement.setString(4, normalizeKey(candidate.getEmailAddress()));
            statement.setString(5, profile.getMobileNumber());
            statement.setString(6, candidate.getPasswordHash());
            statement.setString(7, profile.getGender().name());
            statement.setDate(8, Date.valueOf(profile.getDateOfBirth()));
            statement.setTimestamp(9, Timestamp.valueOf(candidate.getCreatedAt()));
            statement.executeUpdate();
        }
    }

    private Optional<Candidate> findOne(String sql, String value) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, value);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? Optional.of(mapCandidate(resultSet)) : Optional.empty();
            }
        } catch (SQLException exception) {
            throw new DatabaseException("Unable to load candidate account.", exception);
        }
    }

    private Candidate mapCandidate(ResultSet resultSet) throws SQLException {
        CandidateProfile profile = new CandidateProfile(
                resultSet.getString("full_name"),
                resultSet.getString("mobile_number"),
                Gender.valueOf(resultSet.getString("gender")),
                resultSet.getDate("date_of_birth").toLocalDate()
        );

        return new Candidate(
                resultSet.getString("candidate_id"),
                resultSet.getString("username"),
                resultSet.getString("email"),
                resultSet.getString("password_hash"),
                profile,
                resultSet.getTimestamp("created_at").toLocalDateTime()
        );
    }

    private String normalizeKey(String value) {
        return value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
    }
}
