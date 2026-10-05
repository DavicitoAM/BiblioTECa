-- ============================================================
-- SOLO DESARROLLO
-- RESET_DEV_SCHEMA.sql
--
-- ADVERTENCIA:
-- Borra TODAS las tablas y datos del schema public,
-- incluyendo flyway_schema_history.
-- ============================================================

DROP SCHEMA public CASCADE;
CREATE SCHEMA public;
GRANT ALL ON SCHEMA public TO CURRENT_USER;
