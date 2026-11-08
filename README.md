# Silsila

Silsila (سلسلة, "chain") is a knowledge network that links Quranic verses to their tafsir, supporting hadith, fiqh issues (masail), the four madhabs' positions, and the statute (qanun) that codifies the ruling. A student can start from an issue and see the full chain of evidence, and compare how each school ruled.

**MVP scope:** fiqh issues on **nikah (marriage)** and **talaq (divorce)** only.
Every link in the system is reviewed by a scholar before students can see it.

## Team and roles

| Member | Role | Responsibilities |
|--------|------|------------------|
|JAMAL INTALO | [Data and Domain Lead] | `domain` and `dataaccess` packages, database schema, curated nikah/talaq data, domain class diagram |
| HAMAM | [Logic and Interface Lead] | `application` and `presentation` packages, web pages, requirements, use case diagram, layered class diagram |
| Both | Integration | Agree on interfaces first, test the full flow, final report and submission |

## Features
- Browse issues by category (nikah, talaq) and search by keyword
- View the full chain of an issue: verses and their tafsir, hadith, madhab
  positions, and statute
- Compare the four madhabs' positions, with evidence and illah (reasoning)
- Contributors propose links between an issue and a verse, hadith, or statute
- Reviewers log in, then approve or reject proposed links
- Only approved links are visible to students

## Actors
| Actor | What they do |
|-------|--------------|
| Student | Browses, searches, views chains, compares madhabs (no login) |
| Contributor | Logs in and proposes links |
| Reviewer | Everything a contributor does, plus reviews, approves, and rejects links |

## Architecture
Silsila uses a **layered architecture**. A request only travels downwards:
Presentation → Application → Data Access → Database.

| Layer | Responsibility | Main classes |
|-------|----------------|--------------|
| Presentation | Web pages: search/browse and curator forms | `SearchController`, `MasalaController`, `LinkController`, `ReviewController`, `AuthController` |
| Application | Logic: building the chain, review workflow, login | `MasalaService`, `LinkService`, `AuthService`, `MasalaChain` |
| Data Access | Queries and storage of data and links | `MasalaDAO`, `AyahDAO`, `TafsirDAO`, `HadithDAO`, `QanunDAO`, `MadhabPositionDAO`, `LinkDAO`, `UserDAO` |
| Database | Verses, tafsir, hadith, issues, positions, statutes, links, users | H2 database |

Domain classes used by all layers: `Ayah`, `Tafsir`, `Hadith`, `Masala`,
`MadhabPosition`, `Qanun`, `Link` (`AyahLink`, `HadithLink`, `QanunLink`),
`User` (`Contributor`, `Reviewer`).

**Why layered:** each layer can be built and tested on its own, a layer can be replaced without touching the others (for example, adding AI-suggested links later), and verified links stay protected in the inner layers.

## Project structure
```
silsila/
├── docs/
│   ├── 01-requirements/     requirements and user stories
│   ├── 02-architecture/     architecture document and decisions
│   ├── 03-design/           use case and class diagrams (.puml + img/)
│   └── 04-data/             linking guidelines
├── data/
│   ├── raw/                 sources as collected
│   └── curated/             verified data loaded into the database
├── src/main/java/com/silsila/
│   ├── presentation/        controllers
│   ├── application/         services
│   ├── dataaccess/          DAO interfaces and implementations
│   └── domain/              entity classes and enums
├── src/main/resources/      templates (web pages), schema.sql, data.sql
├── src/test/java/           tests
├── pom.xml
└── README.md
```

## Requirements
- Java JDK 22 or newer (check with `java -version`)
- Apache Maven 3.9 or newer (check with `mvn -version`)
- Git (check with `git --version`)
- A web browser

No database installation is needed. Silsila uses the embedded H2 database.

## Getting started
1. Clone the repository:
   ```
   git clone https://github.com/JamalIntalo/Silsila-.git
   cd silsila
   ```
2. Build the project:
   ```
   mvn clean install
   ```
3. Run it:
   ```
   mvn spring-boot:run
   ```
4. Open **http://localhost:8080** in your browser.

To run the tests: `mvn test`

## Default accounts (for testing)
| Role | Username | Password |
|------|----------|----------|
| Contributor | [contributor1] | [password] |
| Reviewer | [reviewer1] | [password] |

## Documentation
| Document | Location | What it contains |
|----------|----------|------------------|
| Requirements | `docs/01-requirements/` | What the system must do and the quality it must have |
| Architecture | `docs/02-architecture/` | The layered design and why it was chosen |
| Use case diagram | `docs/03-design/use-case-diagram.puml` | Who uses the system and for what |
| Domain class diagram | `docs/03-design/class-diagram-domain.puml` | The entities and how they relate |
| Layered class diagram | `docs/03-design/class-diagram-layers.puml` | Controllers, services, and DAOs per layer |
| Linking guidelines | `docs/04-data/linking-guidelines.md` | Rules for what counts as a valid link |

## Updating this project
```
git pull
git add .
git commit -m "short description"
git push
```

## Course
[Course name and code] - Islamic University of Madina - Submission [date]

## Data sources and credits

The text of the verses, tafsir and hadith is not typed into this project. It is loaded
when the program starts from free public data on GitHub (served through the jsDelivr CDN)
and saved in the `cache` folder, so the project also works without internet.

| Content | Source |
|---|---|
| Qur'an, Arabic text (Uthmani) | fawazahmed0/quran-api, edition `ara-quranuthmanihaf` |
| Qur'an, English translation | fawazahmed0/quran-api, edition `eng-ummmuhammad` (Umm Muhammad, Saheeh International) |
| Tafsir Ibn Kathir (English) | spa5k/tafsir_api, edition `en-tafisr-ibn-kathir` |
| Hadith (Arabic and English) and grades | fawazahmed0/hadith-api, editions `ara-` and `eng-` of abudawud, bukhari and muslim |

Websites: https://github.com/fawazahmed0/quran-api, https://github.com/fawazahmed0/hadith-api,
https://github.com/spa5k/tafsir_api

The madhab positions, book citations and the law article are draft data written by the
project team and must be verified against the original books.