-- =====================================================================
-- BiblioTECa
-- V3__system_users_and_access_ownership.sql
--
-- Completa el modelo de control de acceso separando:
--   1) la PERSONA que entra a la biblioteca;
--   2) el USUARIO DEL SISTEMA que opera/autoriza el registro.
--
-- Compatible con el esquema ya creado por V1 y V2.
-- NO borra personas, accesos ni datos existentes.
-- =====================================================================

-- ---------------------------------------------------------------------
-- USUARIOS INTERNOS DEL SISTEMA
--
-- No representa estudiantes/visitantes que únicamente registran acceso.
-- Representa cuentas con sesión interna, por ejemplo:
--   ADMIN
--   RECEPTION
--   LIBRARIAN
--
-- person_id es opcional porque una cuenta interna puede asociarse a una
-- persona existente de la institución, pero no es obligatorio.
-- ---------------------------------------------------------------------
CREATE TABLE system_users (
    id BIGSERIAL PRIMARY KEY,

    person_id BIGINT,

    username VARCHAR(80) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,

    display_name VARCHAR(150) NOT NULL,
    email VARCHAR(150),

    role VARCHAR(20) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,

    last_login_at TIMESTAMPTZ,

    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_system_users_person
        FOREIGN KEY (person_id)
        REFERENCES persons(id)
        ON DELETE SET NULL,

    CONSTRAINT uq_system_users_person
        UNIQUE (person_id),

    CONSTRAINT chk_system_users_role
        CHECK (role IN ('ADMIN', 'RECEPTION', 'LIBRARIAN'))
);

-- Nombre de usuario sin distinguir mayúsculas/minúsculas.
CREATE UNIQUE INDEX uq_system_users_username_ci
    ON system_users (LOWER(username));

-- Correo opcional, pero único si existe.
CREATE UNIQUE INDEX uq_system_users_email_ci
    ON system_users (LOWER(email))
    WHERE email IS NOT NULL;


-- ---------------------------------------------------------------------
-- ORIGEN OPERATIVO DEL REGISTRO
--
-- source ya indica el canal/dispositivo (por ejemplo KIOSK).
-- capture_mode indica QUIÉN realizó la captura:
--
-- SELF_SERVICE -> la propia persona usa el kiosco/interfaz pública.
-- RECEPTION    -> recepción/bibliotecario captura por la persona.
-- ADMIN        -> un administrador registra/corrige desde panel interno.
-- ---------------------------------------------------------------------
ALTER TABLE access_records
    ADD COLUMN capture_mode VARCHAR(20) NOT NULL DEFAULT 'SELF_SERVICE';

ALTER TABLE access_records
    ADD COLUMN registered_by_user_id BIGINT;

ALTER TABLE access_records
    ADD CONSTRAINT fk_access_records_registered_by
        FOREIGN KEY (registered_by_user_id)
        REFERENCES system_users(id)
        ON DELETE SET NULL;

ALTER TABLE access_records
    ADD CONSTRAINT chk_access_records_capture_mode
        CHECK (
            capture_mode IN ('SELF_SERVICE', 'RECEPTION', 'ADMIN')
        );

-- En autoservicio no existe operador interno.
-- En recepción/admin sí debe existir un usuario del sistema.
ALTER TABLE access_records
    ADD CONSTRAINT chk_access_records_capture_operator
        CHECK (
            (
                capture_mode = 'SELF_SERVICE'
                AND registered_by_user_id IS NULL
            )
            OR
            (
                capture_mode IN ('RECEPTION', 'ADMIN')
                AND registered_by_user_id IS NOT NULL
            )
        );

CREATE INDEX idx_access_records_registered_by
    ON access_records(registered_by_user_id, checked_in_at DESC);

CREATE INDEX idx_access_records_capture_mode
    ON access_records(capture_mode, checked_in_at DESC);


COMMENT ON TABLE system_users IS
    'Cuentas internas que operan BiblioTECa. No sustituye a persons.';

COMMENT ON COLUMN access_records.capture_mode IS
    'SELF_SERVICE, RECEPTION o ADMIN. Define cómo fue capturado el acceso.';

COMMENT ON COLUMN access_records.registered_by_user_id IS
    'Usuario interno que capturó el acceso; NULL únicamente en autoservicio.';
