# BiblioTECa — Sistema de Gestión Bibliotecaria ITSP

Primera versión funcional del sistema para la **Biblioteca del Instituto Tecnológico Superior de Pátzcuaro**.

## Estado actual

- Backend con Spring Boot.
- Frontend con React + TypeScript.
- PostgreSQL.
- Flyway.
- API REST.
- Consulta de estudiante por número de control.
- Catálogo de motivos de visita.
- Registro real de ingresos.
- Navegación inicial del frontend.

## Arquitectura actual

```text
React + TypeScript
        |
        | REST / JSON
        v
Spring Boot
        |
        | JPA / Hibernate
        v
PostgreSQL
```

## Estructura recomendada

```text
BiblioTECa/
├── backend/
├── frontend/
├── database/
│   ├── README.md
│   ├── schema.sql
│   └── seed.sql
├── docs/
│   └── MEMORIA_TECNICA.md
├── .gitignore
└── README.md
```

## Base de datos

Nombre sugerido:

```text
biblioteca_itsp
```

La fuente de verdad del esquema durante el desarrollo debe ser **Flyway**:

```text
backend/src/main/resources/db/migration/
```

Los archivos de `database/` funcionan como respaldo documental y reconstrucción manual.

## Ejecución local

Backend:

```powershell
cd backend
$env:SERVER_PORT="8081"
.\mvnw.cmd spring-boot:run
```

Frontend:

```powershell
cd frontend
npm install
npm run dev
```

Aplicación:

```text
http://localhost:5173
```

API:

```text
http://localhost:8081
```

## Endpoints implementados

```http
GET /api/v1/students/control-number/{controlNumber}
GET /api/v1/visit-reasons
POST /api/v1/visits
```

Ejemplo de registro:

```json
{
  "controlNumber": "22123456",
  "visitReasonId": 3
}
```

La fecha y hora del ingreso se generan en el backend.

## Próximas etapas

- Mejorar UX e identidad visual.
- Catálogo bibliográfico.
- Inventario de ejemplares.
- Préstamos, devoluciones y renovaciones.
- Cubículos y reservas.
- Panel administrativo.
- Seguridad, roles y permisos.
- Auditoría.
- Reportes y automatizaciones.
