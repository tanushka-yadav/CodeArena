# Changelog

All notable changes to CodeArena will be documented in this file.

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
