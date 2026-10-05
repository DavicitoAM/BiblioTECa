-- =====================================================================
-- BiblioTECa - VERIFY_FINAL_MODEL.sql
-- Script de verificación. No modifica datos.
-- =====================================================================

-- 1. Migraciones aplicadas
SELECT
    installed_rank,
    version,
    description,
    success
FROM flyway_schema_history
ORDER BY installed_rank;

-- 2. Tablas del modelo
SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'public'
  AND table_type = 'BASE TABLE'
ORDER BY table_name;

-- 3. Vistas operativas
SELECT table_name
FROM information_schema.views
WHERE table_schema = 'public'
ORDER BY table_name;

-- 4. Tipos de persona
SELECT id, code, name, active
FROM person_types
ORDER BY id;

-- 5. Personas
SELECT *
FROM v_person_directory
ORDER BY paternal_surname, maternal_surname, first_name;

-- 6. Personas actualmente dentro
SELECT *
FROM v_current_occupancy
ORDER BY checked_in_at DESC;

-- 7. Últimos 50 accesos
SELECT *
FROM v_access_history
ORDER BY checked_in_at DESC
LIMIT 50;

-- 8. Usuarios internos del sistema
-- La tabla puede estar vacía hasta implementar autenticación.
SELECT
    id,
    person_id,
    username,
    display_name,
    email,
    role,
    active,
    last_login_at,
    created_at,
    updated_at
FROM system_users
ORDER BY id;
