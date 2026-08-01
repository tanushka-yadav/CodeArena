# CodeArena - Coding Test Platform

CodeArena is a professional Java Swing desktop application for managing coding tests, candidates, questions, submissions, results, and leaderboards.

## Tech Stack

- Java 21
- Maven
- Java Swing
- MVC architecture
- MySQL planned for persistence

## Current Version

`0.1.0-SNAPSHOT`

## Folder Structure

```text
src/main/java/com/codearena
├── CodeArenaApplication.java
├── config
├── constants
├── controller
├── database
├── enums
├── exception
├── interfaces
├── model
├── repository
├── service
├── util
├── validator
└── view
```

## Run

```bash
mvn clean compile
mvn exec:java
```

The first milestone can also be compiled directly with `javac` when Maven is not installed.

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

