# Base de datos — BiblioTECa

## Motor
PostgreSQL.

## Nombre sugerido
`biblioteca_itsp`

## Fuente de verdad
Flyway:

```text
backend/src/main/resources/db/migration/
```

Esta carpeta funciona como memoria técnica y respaldo manual.

## Recreación manual

1. Crear una base vacía `biblioteca_itsp`.
2. Ejecutar `schema.sql`.
3. Opcionalmente ejecutar `seed.sql`.

## Tablas actuales

- `careers`
- `students`
- `visit_reasons`
- `visits`

## Relaciones

```text
careers 1 --- N students
students 1 --- N visits
visit_reasons 1 --- N visits
```

## Criterio
`students` conserva únicamente información escolar mínima necesaria para servicios bibliotecarios.
