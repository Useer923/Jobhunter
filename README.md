# 🎯 JobHunter

> Application full-stack de veille d'offres d'emploi, construite avec **Spring Boot** et **Angular**, intégrant un pipeline ELT temps réel depuis **The Muse API**.

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

---

## ⚡ Lancer le projet

### Prérequis
- Java 21+
- Maven 3.8+
- Node.js 18+ et npm
- Angular CLI (`npm install -g @angular/cli`)

### Backend

```bash
cd backend
mvn spring-boot:run
```

L'API démarre sur **http://localhost:8080**

### Frontend

```bash
cd frontend
npm install
ng serve
```

L'app démarre sur **http://localhost:4200**

### Importer les offres depuis The Muse API

```bash
curl -X POST http://localhost:8080/api/offres/fetch
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
│   └── offre.model.ts          # Interface TypeScript — typage des données
├── services/
│   └── offre.service.ts        # Catalogue des appels API
└── components/
    └── offre-list/
        ├── offre-list.component.ts   # Logique du composant
        └── offre-list.component.html # Template HTML
```

---

## 💡 Fonctionnalités

- ✅ Pipeline ELT automatisé depuis The Muse API
- ✅ Recherche en temps réel côté Angular
- ✅ Filtrage par niveau et catégorie
- ✅ Lien direct vers chaque offre originale
- ✅ Architecture en couches Entity / Repository / Service / Controller
- ✅ CORS configuré pour la communication Angular ↔ Spring Boot
- ✅ Gestion des nulls et des descriptions HTML longues

---

## 🔮 Évolutions prévues

- [ ] Dockerisation (Dockerfile + docker-compose)
- [ ] CI/CD GitHub Actions
- [ ] Score de matching offre / profil candidat
- [ ] Authentification JWT
- [ ] Persistance PostgreSQL en production

---

## 👤 Auteur

**Thomas Baldinot** — [LinkedIn](https://www.linkedin.com/in/Useer923) · [GitHub](https://github.com/Useer923)
