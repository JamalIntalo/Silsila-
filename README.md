# Silsila-
# Fiqh Knowledge Graph

A knowledge network that links Quranic verses to their tafsir, ruling hadith,
fiqh issues, madhab disagreements, and related statutes, so a researcher can
trace a legal ruling from its source to its modern law.

**MVP scope:** fiqh issues on **nikah (marriage)** and **talaq (divorce)**.

## Team
- JAMAL INTALO - [role, e.g. data layer and domain model]
- HAMAM - [role, e.g. services and interface]

## Problem
Legal-ruling verses and hadiths are spread across many sources. Linking them
to fiqh issues, the schools' differences, and statutes is slow and manual.
This system stores those links in one place.

## Features
- Search by verse and view its tafsir, hadith, fiqh issue, and statute
- Compare the four madhabs' rulings on an issue
- View the full chain for any issue
- Create and verify links between sources (scholar review)
- User login with roles (Researcher, Scholar, Admin)

## Architecture
Layered architecture:

| Layer | Responsibility |
|-------|----------------|
| Presentation | Controllers handle user requests |
| Service | Business logic (search, linking, auth) |
| Data | Repositories access the database |
| Domain | Entities: Verse, Tafsir, Hadith, FiqhIssue, Madhab, MadhabRuling, Statute |

Design diagrams are in [`docs/03-design/`](docs/03-design/).

## Project structure
```
docs/        requirements, architecture, diagrams, linking guidelines
data/        raw sources and curated, verified links
src/         source code (domain, data, services, controllers)
tests/       tests
```

## Getting started
1. Clone the repo: `git clone https://github.com/JamalIntalo/Silsila-.git`
2. Install requirements: [e.g. Java 17 / Python 3.11]
3. Run: [command to run your project]

## Documentation
- Requirements: `docs/01-requirements/`
- Architecture: `docs/02-architecture/`
- Use case and class diagrams: `docs/03-design/`
- Linking guidelines: `docs/04-data/`

## Course
Software Engineering  - Islamic University of Madina - Submission [date]