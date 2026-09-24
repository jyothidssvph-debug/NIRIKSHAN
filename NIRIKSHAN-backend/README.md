# NIRIKSHAN Backend
Java 17 + Spring Boot. Runs locally on port 8080.

## Flow
CSV Import -> Validation -> Risk Engine -> Dashboard

## Run
Open this folder in IntelliJ and run `NirikshanApplication.java`.

## API
GET /api/summary
GET /api/works
GET /api/risks
GET /api/works/{id}
GET /api/works/{id}/risk
POST /api/import (multipart field: file)

The bundled CSV is synthetic demonstration data.
