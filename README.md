# LiS Keuzetool — Java (Spring Boot) + Static React

## Snel starten
1) Installeer Java 17+ en Maven.
2) Vul `src/main/resources/application.properties` in:
   - MySQL: `spring.datasource.username` / `spring.datasource.password`
   - Mail: `spring.mail.*`, `app.mail.from`, `app.mail.to.rob`
3) Start:
```bash
mvn spring-boot:run
```
4) Open: http://localhost:8080/

De front-end staat in `src/main/resources/static/index.html` en gebruikt React + Tailwind via CDN.

## Endpoints
- POST `/api/send-lis-advice`        → mail naar Rob + advies opslaan
- POST `/api/send-lis-advice-user`   → mail naar bezoeker

## Database
JPA maakt tabellen automatisch aan (`ddl-auto=update`).
