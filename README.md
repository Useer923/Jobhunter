# 🎯 JobHunter
 
> Application full-stack de veille d'offres d'emploi data, construite avec **Spring Boot** et **Angular**, intégrant un pipeline ELT temps réel depuis **The Muse API**.
 
---
 
## 🏗️ Architecture
 
```
┌─────────────────┐         ┌─────────────────┐         ┌─────────────────┐
│                 │  HTTP   │                 │   ELT   │                 │
│  Angular 19     │ ──────► │  Spring Boot    │ ──────► │  The Muse API   │
│  (Frontend)     │         │  (Backend API)  │         │  (Job Boards)   │
│  port 4200      │ ◄────── │  port 8080      │         │                 │
└─────────────────┘  JSON   └────────┬────────┘         └─────────────────┘
                                     │
                                     ▼
                            ┌─────────────────┐
                            │   H2 Database   │
                            │  (In-Memory)    │
                            └─────────────────┘
```
 
---
 
## 🚀 Stack Technique
 
### Backend
| Technologie | Rôle |
|---|---|
| Java 21 + Spring Boot 3.5 | Framework backend |
| Spring Data JPA + Hibernate | ORM — génération SQL automatique |
| H2 Database | Base de données in-memory |
| RestTemplate | Appels HTTP vers The Muse API |
| Jackson ObjectMapper | Parsing JSON |
 
### Frontend
| Technologie | Rôle |
|---|---|
| Angular 19 | Framework frontend SPA |
| TypeScript | Typage statique |
| HttpClient + RxJS | Appels API asynchrones |
| Nginx | Serveur web en production (Docker) |
 
---
 
## ⚡ Lancer le projet
 
### 🐳 Mode Docker — recommandé (zéro configuration)
 
**Prérequis :** Docker + Docker Compose installés.
 
```bash
docker-compose up --build
```
 
C'est tout. Docker va :
1. Compiler le backend Spring Boot
2. Compiler le frontend Angular
3. Démarrer les deux services
4. **Fetcher automatiquement les offres depuis The Muse API au démarrage**
Ouvre **http://localhost:4200** — les offres sont déjà là.
 
Pour arrêter :
```bash
docker-compose down
```
 
---
 
### 🛠️ Mode Manuel — sans Docker
 
**Prérequis :**
- Java 21+
- Maven 3.8+
- Node.js 18+ et npm
- Angular CLI (`npm install -g @angular/cli`)
**1. Lancer le backend :**
```bash
cd backend
mvn spring-boot:run
```
L'API démarre sur **http://localhost:8080**
 
Les offres sont fetchées automatiquement au démarrage depuis The Muse API.
 
**2. Lancer le frontend** (dans un nouveau terminal) :
```bash
cd frontend
npm install
ng serve
```
L'app démarre sur **http://localhost:4200**
 
**3. Importer manuellement les offres depuis The Muse API (optionnel) :**
```bash
curl -X POST http://localhost:8080/api/offres/fetch
```
 
**4. Ajouter une offre manuellement (optionnel) :**
```bash
curl -X POST http://localhost:8080/api/offres \
-H "Content-Type: application/json" \
-d '{
  "titre": "Data Scientist",
  "entreprise": "ADP",
  "localisation": "Nanterre",
  "description": "Poste de Data Scientist dans le programme graduate ADP",
  "niveau": "Entry Level",
  "categorie": "Data and Analytics",
  "lienOffre": "https://www.adp.com/careers",
  "datePublication": "2026-05-01"
}'
```
 
---
 
## 📡 Endpoints API
 
| Méthode | Endpoint | Description |
|---|---|---|
| `GET` | `/api/offres` | Toutes les offres |
| `GET` | `/api/offres/{id}` | Une offre par id |
| `GET` | `/api/offres/search?keyword=` | Recherche par mot clé |
| `GET` | `/api/offres/niveau/{niveau}` | Filtrer par niveau |
| `GET` | `/api/offres/categorie/{categorie}` | Filtrer par catégorie |
| `POST` | `/api/offres` | Créer une offre manuellement |
| `POST` | `/api/offres/fetch` | Importer depuis The Muse API |
| `DELETE` | `/api/offres/{id}` | Supprimer une offre |
 
---
 
## 🏛️ Architecture Backend
 
```
com/adp/jobhunter/
├── DataInitializer.java        # Fetch automatique au démarrage (@EventListener)
├── entity/
│   └── Offre.java              # Modèle JPA → table SQL auto-générée
├── repository/
│   └── OffreRepository.java    # Accès BDD — requêtes générées depuis noms de méthodes
├── service/
│   ├── OffreService.java       # Logique métier
│   └── MuseService.java        # Pipeline ELT → The Muse API
└── controller/
    └── OffreController.java    # Endpoints REST
```
 
### Pipeline ELT dans MuseService
 
```
Extract  → Appel HTTP GET vers The Muse API (RestTemplate)
Transform → Parsing JSON + mapping vers entité Offre (ObjectMapper)
Load     → Insertion en base H2 (offreRepository.saveAll)
```
 
---
 
## 🏛️ Architecture Frontend
 
```
src/app/
├── models/
│   └── offre.model.ts              # Interface TypeScript — typage des données
├── services/
│   └── offre.service.ts            # Catalogue des appels API
└── components/
    └── offre-list/
        ├── offre-list.component.ts   # Logique du composant
        └── offre-list.component.html # Template HTML
```
 
---
 
## 🐳 Structure Docker
 
```
Jobhunter/
├── docker-compose.yml          # Orchestration des deux services
├── backend/
│   └── Dockerfile              # Multi-stage : Maven build → JRE runtime
└── frontend/
    └── Dockerfile              # Multi-stage : Node build → Nginx runtime
```
 
### Multi-stage build
 
**Backend :**
```
Stage 1 — maven:3.9-eclipse-temurin-21
  └── mvn clean package → jobhunter.jar
 
Stage 2 — eclipse-temurin:21-jre (image légère)
  └── java -jar jobhunter.jar
```
 
**Frontend :**
```
Stage 1 — node:22-alpine
  └── npm run build → dist/
 
Stage 2 — nginx:alpine (image légère)
  └── sert les fichiers statiques Angular
```
 
---
 
## 💡 Fonctionnalités
 
- ✅ Pipeline ELT automatisé depuis The Muse API (Data & Analytics + Software Engineering)
- ✅ Fetch automatique au démarrage de l'application
- ✅ Recherche en temps réel côté Angular
- ✅ Lien direct vers chaque offre originale sur The Muse
- ✅ Architecture en couches Entity / Repository / Service / Controller
- ✅ CORS configuré pour la communication Angular ↔ Spring Boot
- ✅ Conteneurisation Docker avec docker-compose
---
 
## 👤 Auteur
 
**Thomas Baldinot** — [LinkedIn](https://www.linkedin.com/in/profil-de-thomas-b-sur-link) · [GitHub](https://github.com/Useer923)