-- ============================================================
-- DATOS DE PRUEBA OPCIONALES - NO ES MIGRACIÓN FLYWAY
-- Ejecutar manualmente en pgAdmin únicamente para probar API.
-- Ajusta la carrera antes de usarla si quieres probar estudiante.
-- ============================================================

-- Carrera de prueba
INSERT INTO careers (code, name)
VALUES ('TEST-ISC', 'Carrera de prueba')
ON CONFLICT (code) DO NOTHING;

-- Estudiante
WITH type_row AS (
    SELECT id FROM person_types WHERE code = 'STUDENT'
), inserted_person AS (
    INSERT INTO persons (
        person_type_id,
        institutional_identifier,
        first_name,
        paternal_surname,
        maternal_surname
    )
    SELECT id, 'TEST-EST-001', 'Ana', 'Prueba', 'Estudiante'
    FROM type_row
    ON CONFLICT (institutional_identifier) DO NOTHING
    RETURNING id
)
INSERT INTO student_profiles (person_id, career_id, semester, academic_status)
SELECT
    inserted_person.id,
    careers.id,
    4,
    'ACTIVE'
FROM inserted_person
JOIN careers ON careers.code = 'TEST-ISC'
ON CONFLICT (person_id) DO NOTHING;

-- Docente
INSERT INTO persons (
    person_type_id,
    institutional_identifier,
    first_name,
    paternal_surname,
    maternal_surname
)
SELECT id, 'TEST-DOC-001', 'Laura', 'Prueba', 'Docente'
FROM person_types
WHERE code = 'TEACHER'
ON CONFLICT (institutional_identifier) DO NOTHING;

-- Personal
INSERT INTO persons (
    person_type_id,
    institutional_identifier,
    first_name,
    paternal_surname,
    maternal_surname
)
SELECT id, 'TEST-STAFF-001', 'Mario', 'Prueba', 'Personal'
FROM person_types
WHERE code = 'STAFF'
ON CONFLICT (institutional_identifier) DO NOTHING;
