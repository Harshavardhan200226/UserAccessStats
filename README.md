# GitHub Organization User Access Report

This is a Spring Boot application that generates a report of user access to repositories within a GitHub organization.

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL Server running on localhost:3306
- A GitHub Personal Access Token with `read:org` and `read:user` permissions

## Setup

1. Ensure MySQL is running on localhost:3306.
2. Clone this repository.
3. The GitHub token is already configured in the `.env` file. If you need to change it, edit the `.env` file in the project root.

## Running the Application

Run the application using Maven:
```
mvn spring-boot:run
```

The application will start on port 8080.

## API Usage

### Get Access Report

**Endpoint:** `GET /api/access-report/{organization}`

**Example:**
```
curl http://localhost:8080/api/access-report/your-org-name
```

**Response:**
```json
{
  "organization": "your-org-name",
  "userAccess": {
    "user1": ["repo1", "repo2"],
    "user2": ["repo2"]
  }
}
```

## Authentication Configuration

The application uses a GitHub Personal Access Token for authentication. The token is loaded from the `.env` file in the project root. The token needs the following scopes:
- `read:org` - to read organization repositories
- `read:user` - to read user information

If you need to use a different token, update the `GITHUB_TOKEN` value in the `.env` file.

## Assumptions and Design Decisions

- The application assumes the provided token has access to the specified organization.
- MySQL database is configured for potential future features like caching or storing reports.
- For efficiency, the application fetches all repositories and their collaborators sequentially. For very large organizations, this may take time due to API rate limits (5000 requests/hour for authenticated users).
- The report aggregates users and their access to repositories as a list of repository names.
- Error handling is basic; in production, more robust error handling and logging would be added.