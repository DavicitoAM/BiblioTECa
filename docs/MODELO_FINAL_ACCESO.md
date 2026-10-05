# Modelo final propuesto — BiblioTECa / Control de acceso v3

Este modelo cierra el dominio **de control de acceso**.

## Entidades persistentes

1. `PersonType`
2. `Person`
3. `Career`
4. `StudentProfile`
5. `VisitReason`
6. `AccessRecord`
7. `SystemUser`

`STUDENT`, `TEACHER`, `STAFF` y `VISITOR` son categorías de `Person`; no son
tablas separadas.

`ADMIN`, `RECEPTION` y `LIBRARIAN` son roles de `SystemUser`; no son
personas que ingresan por el hecho de tener una cuenta.

```mermaid
erDiagram
    PERSON_TYPES ||--o{ PERSONS : classifies
    PERSONS ||--o| STUDENT_PROFILES : may_have
    CAREERS ||--o{ STUDENT_PROFILES : belongs_to
    PERSONS ||--o{ ACCESS_RECORDS : enters
    VISIT_REASONS ||--o{ ACCESS_RECORDS : explains
    PERSONS ||--o| SYSTEM_USERS : may_be_linked
    SYSTEM_USERS ||--o{ ACCESS_RECORDS : registers

    PERSON_TYPES {
        bigint id PK
        varchar code UK
        varchar name
        varchar description
        boolean active
    }

    PERSONS {
        bigint id PK
        bigint person_type_id FK
        varchar institutional_identifier UK
        varchar first_name
        varchar paternal_surname
        varchar maternal_surname
        varchar email
        varchar phone
        boolean active
    }

    STUDENT_PROFILES {
        bigint person_id PK,FK
        bigint career_id FK
        smallint semester
        varchar academic_status
    }

    CAREERS {
        bigint id PK
        varchar code UK
        varchar name
        boolean active
    }

    VISIT_REASONS {
        bigint id PK
        varchar code UK
        varchar name
        varchar description
        integer display_order
        boolean active
    }

    SYSTEM_USERS {
        bigint id PK
        bigint person_id FK,UK
        varchar username
        varchar password_hash
        varchar display_name
        varchar email
        varchar role
        boolean active
        timestamptz last_login_at
    }

    ACCESS_RECORDS {
        bigint id PK
        bigint person_id FK
        bigint visit_reason_id FK
        bigint registered_by_user_id FK
        timestamptz checked_in_at
        timestamptz checked_out_at
        varchar destination
        varchar notes
        varchar source
        varchar capture_mode
    }
```

## Decisión de simplificación

No se crean `Student`, `Teacher`, `Staff` y `Visitor` como cuatro tablas
independientes. Todos son `Person`.

`StudentProfile` existe únicamente porque el estudiante sí tiene datos
académicos exclusivos: carrera, semestre y estado académico.

Tampoco se crea una tabla `Entry` y otra `Exit`: `AccessRecord` contiene ambas
marcas de tiempo.

## Importante sobre alcance

Este modelo deja completo el **control de acceso**.

Si `Catálogo`, `Circulación` o `Espacios` van a convertirse en módulos reales
de biblioteca (libros, ejemplares, préstamos, reservas), requieren su propio
modelo de dominio y NO deben improvisarse dentro de estas tablas.
