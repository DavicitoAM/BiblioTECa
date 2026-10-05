# BiblioTECa — Modelo final de control de acceso v3

## Qué completa esta iteración

Hasta V2 ya existían:

- `person_types`
- `persons`
- `student_profiles`
- `careers`
- `visit_reasons`
- `access_records`

Faltaba representar correctamente a quien **opera el sistema**.

V3 agrega:

- `system_users`
- `access_records.capture_mode`
- `access_records.registered_by_user_id`

Así quedan separados dos conceptos:

```text
Person
= quién entra a la biblioteca

SystemUser
= quién administra o captura dentro del sistema
```

Esto evita convertir a un visitante o estudiante en una cuenta administrativa.

## Clases finales del dominio de acceso

```text
PersonType
Person
Career
StudentProfile
VisitReason
AccessRecord
SystemUser
```

Además:

```text
SystemUserRole
CaptureMode
```

son enums Java.

## Cómo aplicar

### 1. NO vuelvas a borrar la base

V1 y V2 ya están aplicadas y tienes datos útiles.

Copia:

```text
backend/src/main/resources/db/migration/
    V3__system_users_and_access_ownership.sql
    V4__operational_views.sql
```

dentro de la misma carpeta de migraciones de tu proyecto.

### 2. Copia las clases Java

Copia sobre `backend/src/main/java/mx/edu/itspa/biblioteca/`:

```text
auth/
    SystemUser.java
    SystemUserRepository.java
    SystemUserRole.java

access/
    CaptureMode.java
    AccessRecord.java
    AccessRecordResponse.java
```

`AccessRecord.java` y `AccessRecordResponse.java` reemplazan los existentes.

### 3. Arranca normalmente

```powershell
cd C:\BiblioTECa\backend
.\mvnw.cmd clean
.\mvnw.cmd spring-boot:run
```

Flyway ejecutará automáticamente V3 y V4.

NO ejecutes V3/V4 primero en pgAdmin si después vas a dejar que Flyway las
ejecute, porque Flyway debe registrar esas migraciones en
`flyway_schema_history`.

### 4. Verificación

En pgAdmin ejecuta:

```text
database-tools/VERIFY_FINAL_MODEL.sql
```

La tabla `system_users` puede estar vacía. Eso es normal: todavía no se ha
implementado el login administrativo.

## Compatibilidad con el flujo actual

Los accesos que ya existen quedan con:

```text
capture_mode = SELF_SERVICE
registered_by_user_id = NULL
```

El constructor actual de `AccessRecord` también sigue funcionando y registra
autoservicio por defecto.

Por eso el frontend público actual no necesita cambiar sólo por aplicar V3.

## Qué NO hace todavía

Esta entrega deja la persistencia preparada, pero NO implementa aún:

- login administrativo;
- hashing/creación de contraseña;
- sesiones;
- endpoint de gestión de `system_users`.

Eso corresponde al módulo de autenticación.

No se incluye una contraseña por defecto ni una cuenta admin insegura.

## Alcance importante

Esta BD queda cerrada para **control de acceso**.

Si el sistema final va a administrar de verdad:

- catálogo de libros;
- ejemplares;
- préstamos/devoluciones;
- reservas de espacios;

entonces esos módulos requieren entidades adicionales antes de afirmar que
`toda BiblioTECa` está terminada.
