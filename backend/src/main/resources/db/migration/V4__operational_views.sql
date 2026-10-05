-- =====================================================================
-- BiblioTECa
-- V4__operational_views.sql
--
-- Vistas de lectura para simplificar consultas del frontend/admin.
-- No almacenan datos adicionales.
-- =====================================================================

-- ---------------------------------------------------------------------
-- Directorio unificado de personas.
-- Para no obligar al frontend a unir persons + person_types +
-- student_profiles + careers en cada consulta.
-- ---------------------------------------------------------------------
CREATE OR REPLACE VIEW v_person_directory AS
SELECT
    p.id,
    pt.code AS person_type_code,
    pt.name AS person_type_name,
    p.institutional_identifier,
    p.first_name,
    p.paternal_surname,
    p.maternal_surname,
    CONCAT_WS(
        ' ',
        p.first_name,
        p.paternal_surname,
        p.maternal_surname
    ) AS full_name,
    p.email,
    p.phone,
    p.active,
    sp.semester,
    sp.academic_status,
    c.id AS career_id,
    c.code AS career_code,
    c.name AS career_name,
    p.created_at,
    p.updated_at
FROM persons p
JOIN person_types pt
    ON pt.id = p.person_type_id
LEFT JOIN student_profiles sp
    ON sp.person_id = p.id
LEFT JOIN careers c
    ON c.id = sp.career_id;


-- ---------------------------------------------------------------------
-- Personas que actualmente se encuentran dentro.
-- Un acceso está abierto cuando checked_out_at IS NULL.
-- ---------------------------------------------------------------------
CREATE OR REPLACE VIEW v_current_occupancy AS
SELECT
    ar.id AS access_record_id,
    p.id AS person_id,
    pt.code AS person_type_code,
    pt.name AS person_type_name,
    p.institutional_identifier,
    CONCAT_WS(
        ' ',
        p.first_name,
        p.paternal_surname,
        p.maternal_surname
    ) AS full_name,
    vr.id AS visit_reason_id,
    vr.code AS visit_reason_code,
    vr.name AS visit_reason_name,
    ar.destination,
    ar.checked_in_at,
    ar.source,
    ar.capture_mode,
    ar.registered_by_user_id,
    su.display_name AS registered_by_name
FROM access_records ar
JOIN persons p
    ON p.id = ar.person_id
JOIN person_types pt
    ON pt.id = p.person_type_id
LEFT JOIN visit_reasons vr
    ON vr.id = ar.visit_reason_id
LEFT JOIN system_users su
    ON su.id = ar.registered_by_user_id
WHERE ar.checked_out_at IS NULL;


-- ---------------------------------------------------------------------
-- Historial legible de accesos.
-- ---------------------------------------------------------------------
CREATE OR REPLACE VIEW v_access_history AS
SELECT
    ar.id AS access_record_id,
    p.id AS person_id,
    pt.code AS person_type_code,
    pt.name AS person_type_name,
    p.institutional_identifier,
    CONCAT_WS(
        ' ',
        p.first_name,
        p.paternal_surname,
        p.maternal_surname
    ) AS full_name,
    vr.code AS visit_reason_code,
    vr.name AS visit_reason_name,
    ar.destination,
    ar.notes,
    ar.checked_in_at,
    ar.checked_out_at,
    CASE
        WHEN ar.checked_out_at IS NULL THEN 'OPEN'
        ELSE 'CLOSED'
    END AS access_status,
    ar.source,
    ar.capture_mode,
    ar.registered_by_user_id,
    su.display_name AS registered_by_name,
    ar.created_at,
    ar.updated_at
FROM access_records ar
JOIN persons p
    ON p.id = ar.person_id
JOIN person_types pt
    ON pt.id = p.person_type_id
LEFT JOIN visit_reasons vr
    ON vr.id = ar.visit_reason_id
LEFT JOIN system_users su
    ON su.id = ar.registered_by_user_id;
