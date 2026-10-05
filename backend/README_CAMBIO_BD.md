# Remodelación de base de datos BiblioTECa — Modelo v2

Este paquete reemplaza el modelo centrado en `students` por uno centrado en `persons`.

## Archivos a reemplazar

Copia estos archivos sobre los existentes:

```text
backend/src/main/resources/db/migration/V1__initial_schema.sql
backend/src/main/resources/db/migration/V2__development_seed.sql
```

## Por qué hay que reiniciar el schema

Flyway ya registró tus migraciones anteriores en `flyway_schema_history`.
Si sólo editas V1 y V2, Flyway detectará que sus checksums cambiaron.

Como todavía estás en desarrollo y no necesitas conservar datos, reconstruimos el schema.

## Pasos

1. Detén el backend.
2. Reemplaza V1 y V2.
3. En pgAdmin abre `Tools > Query Tool` sobre la base usada por el backend.
4. Ejecuta `database-tools/RESET_DEV_SCHEMA.sql`.
5. Arranca Spring Boot:

```powershell
cd .\backend
.\mvnw.cmd spring-boot:run
```

6. Verifica Flyway:

```sql
SELECT installed_rank, version, description, success
FROM flyway_schema_history
ORDER BY installed_rank;
```

7. Verifica tipos de persona:

```sql
SELECT * FROM person_types ORDER BY id;
```

8. Verifica tablas:

```sql
SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'public'
ORDER BY table_name;
```

Esperadas:

```text
access_records
careers
flyway_schema_history
person_types
persons
student_profiles
visit_reasons
```

## Importante

Después de cambiar la BD, las clases Java antiguas que apunten a `students` y `visits`
ya no coincidirán con el esquema.

El siguiente paso será remodelar las clases:

```text
Student     -> Person + StudentProfile
Visit       -> AccessRecord
Career      -> se conserva
VisitReason -> se conserva
PersonType  -> nueva
```

Hasta actualizar entidades, repositorios, servicios y controladores, no conviene probar
los endpoints antiguos.
