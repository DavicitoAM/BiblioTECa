# Backend v2 sincronizado con la nueva BD

Este backend corresponde al esquema nuevo basado en:

- `person_types`
- `persons`
- `student_profiles`
- `careers`
- `visit_reasons`
- `access_records`

Se eliminaron del código las dependencias directas de las tablas antiguas:

- `students`
- `visits`

## Paquetes principales

```text
mx.edu.itspa.biblioteca
├── person
│   ├── PersonType
│   ├── Person
│   ├── StudentProfile
│   ├── Career
│   ├── repositories
│   ├── DTOs
│   ├── PersonService
│   └── PersonController
└── access
    ├── AccessRecord
    ├── VisitReason
    ├── repositories
    ├── DTOs
    ├── AccessService
    └── AccessController
```

## Endpoints principales

```text
GET  /api/v1/person-types
GET  /api/v1/careers
GET  /api/v1/persons/{id}
GET  /api/v1/persons/identifier/{identifier}
POST /api/v1/persons/visitors

GET  /api/v1/visit-reasons
GET  /api/v1/access-records/open
GET  /api/v1/access-records/recent
POST /api/v1/access-records/check-in
POST /api/v1/access-records/{id}/check-out
```

## Reglas del acceso

- Una persona inactiva no puede registrar entrada.
- Un tipo de persona inactivo no puede registrar entrada.
- Una persona no puede tener dos accesos abiertos.
- Para `VISITOR` son obligatorios motivo y destino.
- Para estudiantes/docentes/personal el motivo es opcional a nivel de backend.
- La salida actualiza el mismo `access_record`; no crea otra fila.

## Configuración

`application.yml` conserva los valores locales por defecto y permite sobreescribirlos:

```text
DB_URL
DB_USERNAME
DB_PASSWORD
SERVER_PORT
```

Hibernate usa:

```yaml
ddl-auto: validate
```

Flyway sigue siendo responsable de construir el esquema.

## Datos opcionales de prueba

`database-tools/DEV_TEST_PEOPLE.sql` crea únicamente datos de prueba para:

- estudiante;
- docente;
- personal.

No es una migración Flyway y no debe ejecutarse en producción.
