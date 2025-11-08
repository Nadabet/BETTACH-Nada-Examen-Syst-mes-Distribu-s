# Architecture Technique du Projet

## Vue d'ensemble

Cette application est basée sur une architecture microservices permettant de gérer des conférences et des keynote speakers.

## Architecture des Microservices

```
┌─────────────────────────────────────────────────────────────────┐
│                         Client Browser                           │
│                      (Angular Frontend)                          │
└────────────────────────────────┬────────────────────────────────┘
                                 │
                                 │ HTTPS
                                 ▼
                    ┌────────────────────────┐
                    │    Keycloak Server     │
                    │   (OAuth2 / OIDC)      │
                    └────────────────────────┘
                                 │
                                 │ Authentication
                                 ▼
                    ┌────────────────────────┐
                    │   API Gateway          │
                    │ (Spring Cloud Gateway) │
                    │  Port: 8888            │
                    └───────────┬────────────┘
                                │
                    ┌───────────┴──────────────┐
                    │                          │
                    ▼                          ▼
         ┌──────────────────┐      ┌──────────────────┐
         │ Service Registry │      │  Config Server   │
         │ (Eureka Server)  │      │(Spring Cloud Cfg)│
         │  Port: 8761      │      │  Port: 8888      │
         └──────────────────┘      └──────────────────┘
                    │
        ┌───────────┴───────────┐
        │                       │
        ▼                       ▼
┌───────────────┐      ┌────────────────────┐
│Keynote Service│      │ Conference Service │
│  Port: 8081   │◄─────┤   Port: 8082       │
└───────┬───────┘      └─────────┬──────────┘
        │                        │
        │                        │ OpenFeign
        ▼                        ▼
  ┌──────────┐            ┌──────────┐
  │  H2/MySQL│            │  H2/MySQL│
  │ Database │            │ Database │
  └──────────┘            └──────────┘
```

## Composants Techniques

### 1. Microservices Fonctionnels

#### Keynote Service (Port: 8081)
- **Responsabilité**: Gestion des keynote speakers
- **Entités**: Keynote (id, nom, prénom, email, fonction)
- **Technologies**:
  - Spring Boot
  - Spring Data JPA
  - H2/MySQL Database
  - OpenAPI/Swagger
  - Resilience4J (Circuit Breaker)
- **Endpoints REST**:
  - GET /api/keynotes - Liste tous les keynotes
  - GET /api/keynotes/{id} - Détails d'un keynote
  - POST /api/keynotes - Créer un keynote
  - PUT /api/keynotes/{id} - Modifier un keynote
  - DELETE /api/keynotes/{id} - Supprimer un keynote

#### Conference Service (Port: 8082)
- **Responsabilité**: Gestion des conférences et reviews
- **Entités**: 
  - Conference (id, titre, type, date, durée, nombreInscrits, score, keynoteId)
  - Review (id, date, texte, note/stars, conferenceId)
- **Technologies**:
  - Spring Boot
  - Spring Data JPA
  - H2/MySQL Database
  - OpenFeign Client (pour communiquer avec Keynote Service)
  - OpenAPI/Swagger
  - Resilience4J (Circuit Breaker)
- **Endpoints REST**:
  - GET /api/conferences - Liste toutes les conférences
  - GET /api/conferences/{id} - Détails d'une conférence
  - POST /api/conferences - Créer une conférence
  - PUT /api/conferences/{id} - Modifier une conférence
  - DELETE /api/conferences/{id} - Supprimer une conférence
  - GET /api/conferences/{id}/reviews - Liste des reviews
  - POST /api/conferences/{id}/reviews - Ajouter une review

### 2. Microservices Techniques

#### Discovery Service - Eureka Server (Port: 8761)
- **Responsabilité**: Service registry pour la découverte de services
- **Technologies**: Spring Cloud Netflix Eureka
- **Fonctionnalités**:
  - Enregistrement automatique des services
  - Health checks
  - Load balancing côté client

#### Config Service - Spring Cloud Config (Port: 8888)
- **Responsabilité**: Centralisation de la configuration
- **Technologies**: Spring Cloud Config Server
- **Fonctionnalités**:
  - Configuration externalisée
  - Configuration par environnement (dev, prod)
  - Refresh dynamique des configurations

#### Gateway Service - Spring Cloud Gateway (Port: 9999)
- **Responsabilité**: Point d'entrée unique pour tous les services
- **Technologies**: Spring Cloud Gateway
- **Fonctionnalités**:
  - Routage dynamique
  - Load balancing
  - Filtres (logging, authentication)
  - Rate limiting
  - Circuit breaker

### 3. Frontend

#### Angular Application (Port: 4200)
- **Technologies**: 
  - Angular 15+
  - TypeScript
  - Bootstrap/Angular Material
  - HttpClient pour les appels REST
- **Fonctionnalités**:
  - Interface de gestion des keynotes
  - Interface de gestion des conférences
  - Gestion des reviews
  - Authentication avec Keycloak

### 4. Sécurité

#### Keycloak (Port: 8080)
- **Responsabilité**: Identity and Access Management
- **Protocoles**: OAuth2, OpenID Connect (OIDC)
- **Fonctionnalités**:
  - Authentification centralisée
  - Gestion des utilisateurs et rôles
  - Single Sign-On (SSO)
  - Token JWT

## Patterns et Bonnes Pratiques

### 1. Circuit Breaker Pattern (Resilience4J)
- Protection contre les défaillances en cascade
- Fallback methods
- Retry mechanism
- Rate limiter

### 2. API Gateway Pattern
- Point d'entrée unique
- Routage intelligent
- Agrégation de services

### 3. Service Discovery Pattern
- Enregistrement dynamique des services
- Load balancing côté client

### 4. Configuration Centralisée
- Configuration externalisée
- Gestion par environnement

### 5. DTO Pattern
- Séparation entre entités et DTOs
- Mappers pour la conversion

### 6. OpenFeign pour la Communication Inter-Services
- Déclaration client REST
- Load balancing automatique
- Intégration avec Eureka

## Technologies et Versions

- **Java**: 17+
- **Spring Boot**: 3.1.x
- **Spring Cloud**: 2022.0.x
- **Angular**: 15+
- **Keycloak**: 21.x
- **Docker**: 20.x
- **Maven**: 3.8+

## Ports des Services

| Service | Port |
|---------|------|
| Keycloak | 8080 |
| Discovery Service (Eureka) | 8761 |
| Config Service | 8888 |
| Gateway Service | 9999 |
| Keynote Service | 8081 |
| Conference Service | 8082 |
| Angular Frontend | 4200 |

## Flux de Communication

1. **Client → Gateway**: Toutes les requêtes passent par la gateway
2. **Gateway → Keycloak**: Validation des tokens JWT
3. **Gateway → Services**: Routage vers le service approprié
4. **Services → Discovery**: Enregistrement et découverte
5. **Services → Config**: Récupération de la configuration
6. **Conference Service → Keynote Service**: Communication via OpenFeign

## Déploiement

### Docker Compose
L'application complète peut être déployée avec Docker Compose incluant:
- Tous les microservices
- Keycloak
- Bases de données MySQL
- Réseau Docker personnalisé

## Stratégie de Développement

1. ✅ Architecture et documentation
2. ✅ Structure Maven multi-modules
3. ✅ Services techniques (Discovery, Config, Gateway)
4. ✅ Keynote Service
5. ✅ Conference Service
6. ✅ Angular Frontend
7. ✅ Sécurité Keycloak
8. ✅ Containerisation Docker
9. ✅ Documentation finale avec screenshots
