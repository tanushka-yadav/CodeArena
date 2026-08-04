# CodeArena - Coding Test Platform

CodeArena is a professional Java Swing desktop application for managing coding tests, candidates, questions, submissions, results, and leaderboards.

## Tech Stack

- Java 21
- Maven
- Java Swing
- MVC architecture
- MySQL planned for persistence

## Current Version

`0.3.0-SNAPSHOT`

## Folder Structure

```text
src/main/java/com/codearena
|-- CodeArenaApplication.java
|-- config
|-- constants
|-- controller
|-- database
|-- dto
|-- enums
|-- exception
|-- interfaces
|-- model
|-- repository
|-- service
|   `-- impl
|-- util
|-- validator
`-- view
    |-- authentication
    |-- components
    `-- registration
```

## Run

```bash
mvn clean compile
mvn exec:java
```

The first milestone can also be compiled directly with `javac` when Maven is not installed.

```bash
javac -d target/classes $(find src/main/java -name "*.java")
java -Djava.awt.headless=true -cp target/classes com.codearena.CodeArenaApplication --smoke-test
```

## Screenshots

Screenshots will be added as features are implemented.

## Roadmap

- Project foundation
- Candidate registration
- Candidate login
- Admin login
- Dashboards
- Question management
- Test engine
- Results, reports, and leaderboard
- MySQL persistence
- Logging and JUnit tests

## Features

### Candidate Registration

- Professional Swing registration form.
- Full-name, username, email, mobile number, password, gender, and date-of-birth validation.
- Reset and back navigation actions.
- In-memory repository boundary prepared for future JDBC persistence.

### Candidate Login

- Professional Swing login form with username/email and password validation.
- Shared PBKDF2 password encoder behind a replaceable `PasswordEncoder` interface.
- Authentication service, session placeholder, remember-me placeholder, forgot-password placeholder, and dashboard placeholder.

## License

This project is licensed under the MIT License.
