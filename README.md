# NIRIKSHAN — Risk Detection & Investigation Platform

NIRIKSHAN is an IntelliJ-based prototype for detecting potential risks in development works and helping investigators understand the evidence behind each risk.

The project keeps the **Spring Boot backend** and **frontend** separate.

## Project Structure

* `NIRIKSHAN-backend` — Spring Boot REST API
* `NIRIKSHAN-frontend` — Frontend dashboard
* `nirikshan_demo_works.csv` — Synthetic demonstration data

## Run in IntelliJ

### Backend

1. Open `NIRIKSHAN-backend` in IntelliJ IDEA.
2. Make sure Java 17+ is installed.
3. Open `src/main/java/com/nirikshan/NirikshanApplication.java`.
4. Run `NirikshanApplication.java`.
5. The backend runs at:

`http://localhost:8080`

### Frontend

1. Open the `NIRIKSHAN-frontend` folder.
2. Open `index.html` in a browser or use IntelliJ's local preview.
3. The frontend connects to the backend at:

`http://localhost:8080`

## Demo Workflow

Use the included synthetic demo data.

1. Load the demo works.
2. Select a work for investigation.
3. Run the risk analysis.
4. Review the detected risk indicators.
5. Open the investigation view.
6. Review the reasons and supporting evidence.
7. Check potentially related works.
8. Compare the related works and similarity signals.
9. Use the information to support further investigation.

## Core Features

* Work data import
* Validation
* Risk analysis
* Risk explanation
* Investigation dashboard
* Potentially related works
* Transparent similarity signals
* Synthetic demonstration dataset
* Spring Boot REST API
* Separate frontend and backend

## Related Works

The backend provides:

`GET /api/works/{id}/related`

The related-works feature identifies potentially related works using transparent similarity signals.

**Important:** Similarity is a screening signal only. It is not proof of fraud, wrongdoing, or liability.

## Data

The included CSV contains **synthetic demonstration data** created for the prototype.

It should not be interpreted as real government records or actual allegations concerning any person, organization, or project.

## GitHub Workflow

1. Open the existing project in IntelliJ.
2. Commit your current version before replacing files if you have local changes.
3. Copy or update the required files.
4. Run the backend and frontend.
5. Test the complete investigation workflow.
6. Test the related-works feature.
7. Commit the changes.
8. Push the branch to GitHub.

## Project Status

**NIRIKSHAN — MVP Prototype**

The system is designed to support human investigation by presenting risk indicators, explanations, evidence, and potentially related works.

Risk indicators and similarity signals require human review and should not be treated as automatic findings of fraud or wrongdoing.
