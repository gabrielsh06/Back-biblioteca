# Back-Biblioteca

API REST para la gestión de una biblioteca. Permite autenticar usuarios mediante
JWT, administrar libros y usuarios, registrar préstamos y consultar información
administrativa del catálogo.

## Tecnologías

- Java 25
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA / Hibernate
- Spring Security y JWT
- PostgreSQL
- Maven Wrapper
- Docker

## Requisitos

- JDK 25
- PostgreSQL 14 o superior
- Una base de datos llamada `biblioteca_db`

También se puede ejecutar mediante Docker. La imagen usa Eclipse Temurin 25 y
expone el puerto definido por la variable `PORT`.

## Configuración

La aplicación lee la configuración desde variables de entorno. Los valores
predeterminados permiten ejecutar localmente con PostgreSQL:

| Variable | Predeterminado | Descripción |
| --- | --- | --- |
| `PORT` | `8080` | Puerto HTTP de la aplicación |
| `DB_URL` | `jdbc:postgresql://localhost:5432/biblioteca_db` | URL JDBC de PostgreSQL |
| `DB_USERNAME` | `postgres` | Usuario de la base de datos |
| `DB_PASSWORD` | — | Contraseña de la base de datos |
| `JWT_SECRET` | valor de desarrollo | Clave usada para firmar los tokens |
| `JWT_EXPIRATION` | `86400000` | Duración del token en milisegundos |
| `CORS_ALLOWED_ORIGINS` | `http://localhost:3000,http://localhost:5173,http://localhost:4200` | Orígenes permitidos separados por coma |

En entornos reales se debe configurar un `JWT_SECRET` propio y seguro. No se
deben usar los valores predeterminados para producción.

## Ejecución local

1. Crear la base de datos:

   ```sql
   CREATE DATABASE biblioteca_db;
   ```

2. Configurar las credenciales:

   ```bash
   export DB_USERNAME=postgres
   export DB_PASSWORD=tu_password
   export JWT_SECRET=una-clave-segura-de-al-menos-256-bits
   ```

3. Iniciar la aplicación:

   ```bash
   ./mvnw spring-boot:run
   ```

   En Windows:

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

Hibernate crea o actualiza las tablas automáticamente con
`spring.jpa.hibernate.ddl-auto=update`.

## Docker

Construir y ejecutar la imagen:

```bash
docker build -t back-biblioteca .
docker run --rm -p 8080:8080 \
  -e DB_URL=jdbc:postgresql://host.docker.internal:5432/biblioteca_db \
  -e DB_USERNAME=postgres \
  -e DB_PASSWORD=tu_password \
  -e JWT_SECRET=una-clave-segura-de-al-menos-256-bits \
  back-biblioteca
```

En Linux, si PostgreSQL se ejecuta en el host, puede ser necesario agregar
`--add-host=host.docker.internal:host-gateway` al comando `docker run`.

## Autenticación

Los endpoints protegidos requieren un token JWT en el encabezado:

```http
Authorization: Bearer <token>
```

Hay dos roles:

- `CUSTOMER`: puede consultar libros y gestionar sus propios préstamos.
- `ADMIN`: puede administrar libros, usuarios y consultar los endpoints
  administrativos.

Registrar un usuario:

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Ana",
    "lastName": "Pérez",
    "email": "ana@example.com",
    "password": "secret123",
    "role": "CUSTOMER"
  }'
```

Iniciar sesión:

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "ana@example.com",
    "password": "secret123"
  }'
```

La respuesta contiene el token y los datos básicos del usuario.

## Endpoints principales

### Autenticación

| Método | Ruta | Acceso |
| --- | --- | --- |
| `POST` | `/api/auth/register` | Público |
| `POST` | `/api/auth/login` | Público |

### Libros

| Método | Ruta | Acceso |
| --- | --- | --- |
| `GET` | `/api/books` | Público |
| `GET` | `/api/books/{id}` | Público |
| `GET` | `/api/books/lookup/{isbn}` | `ADMIN` |
| `POST` | `/api/books` | `ADMIN` |
| `PUT` | `/api/books/{id}` | `ADMIN` |
| `DELETE` | `/api/books/{id}` | `ADMIN` |

`GET /api/books` acepta los parámetros opcionales `search`, `category`, `page`,
`size` y `sort`.

### Préstamos

| Método | Ruta | Acceso |
| --- | --- | --- |
| `POST` | `/api/loans` | Usuario autenticado |
| `GET` | `/api/loans/my-loans` | Usuario autenticado |
| `PUT` | `/api/loans/{id}/return` | Usuario autenticado |
| `GET` | `/api/admin/loans` | `ADMIN` |
| `GET` | `/api/admin/loans/overdue` | `ADMIN` |

Crear un préstamo:

```bash
curl -X POST http://localhost:8080/api/loans \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{"bookId": 1}'
```

### Usuarios y dashboard administrativo

| Método | Ruta | Acceso |
| --- | --- | --- |
| `GET` | `/api/users` | `ADMIN` |
| `GET` | `/api/users/{id}` | `ADMIN` |
| `POST` | `/api/users` | `ADMIN` |
| `PUT` | `/api/users/{id}` | `ADMIN` |
| `DELETE` | `/api/users/{id}` | `ADMIN` |
| `GET` | `/api/admin/dashboard` | `ADMIN` |

El dashboard incluye totales de libros, stock, usuarios, préstamos,
agrupaciones por estado y origen, y los diez libros más prestados.

## Documentación OpenAPI

Con la aplicación iniciada:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Pruebas

Ejecutar todas las pruebas:

```bash
./mvnw test
```

Las pruebas unitarias del dashboard no requieren una base de datos:

```bash
./mvnw test -Dtest=AdminDashboardServiceTest,AdminDashboardControllerTest
```

El test de contexto completo necesita que PostgreSQL esté disponible con las
credenciales configuradas.

## Estructura principal

```text
src/main/java/com/app/backbiblioteca/
├── auth/          # Registro e inicio de sesión
├── admin/         # Dashboard administrativo
├── book/          # Libros, categorías y búsqueda externa
├── loan/          # Préstamos y vencimientos
├── user/          # Gestión de usuarios
├── security/      # JWT, filtros y autorización
└── common/        # DTOs y manejo de errores
```