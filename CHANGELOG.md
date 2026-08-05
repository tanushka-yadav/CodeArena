# Changelog

All notable changes to CodeArena will be documented in this file.

## [0.6.0] - 2026-08-04

### Added

- JDBC/MySQL candidate persistence with automatic candidate table initialization.
- Centralized database configuration and connection factory.
- JDBC candidate repository using prepared statements, transactions, duplicate checks, and try-with-resources.
- MySQL Connector/J Maven dependency.

## [0.5.0] - 2026-08-04

### Added

- Working in-memory candidate registration to login to dashboard flow.
- Automatic navigation from successful registration to candidate login.
- Registration processing state to prevent duplicate submit clicks.
- Smoke-test coverage for duplicate registration, email login, username login, dashboard summary, and logout.

## [0.4.0] - 2026-08-03

### Added

- Candidate dashboard module with welcome banner, status section, quick action cards, and logout flow.
- Dashboard service and controller wired to the existing session manager.
- Professional placeholder windows for coding test, results, leaderboard, profile, settings, and help modules.

## [0.3.0] - 2026-08-03

### Added

- Candidate login module with Swing login window, controller, credential validator, authentication service, and session placeholder.
- Shared password encoder interface with PBKDF2 implementation for registration and login.
- Candidate repository lookup by username or email.
- Login navigation from the startup screen with register, forgot-password, back, and dashboard-placeholder flows.

## [0.2.0] - 2026-08-01

### Added

- Candidate registration module with Swing form, validation, controller, service, and repository layers.
- Reusable rounded Swing controls and shared header/footer components.
- Registration DTOs, candidate models, gender enum, validation constants, and utility classes.

## [0.1.0] - 2026-08-01

### Added

- Maven Java 21 project foundation.
- MVC package structure.
- Runnable Java Swing welcome window.
- GitHub-ready README, MIT license, contribution guide, changelog, and gitignore.
