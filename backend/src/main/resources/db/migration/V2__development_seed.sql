-- ============================================================
-- BiblioTECa - V2__development_seed.sql
-- Datos iniciales para desarrollo
-- ============================================================

INSERT INTO person_types (code, name, description)
VALUES
    ('STUDENT', 'Estudiante', 'Alumno perteneciente a la institución'),
    ('TEACHER', 'Docente', 'Personal docente de la institución'),
    ('STAFF',   'Personal', 'Personal administrativo, técnico o de apoyo'),
    ('VISITOR', 'Visitante', 'Persona externa o ajena a la institución');

INSERT INTO visit_reasons
    (code, name, description, display_order)
VALUES
    ('STUDY', 'Estudio', 'Uso de espacios de la biblioteca para estudio', 10),
    ('CONSULTATION', 'Consulta de material', 'Consulta de libros, revistas u otros materiales', 20),
    ('LOAN_RETURN', 'Préstamo o devolución', 'Trámite relacionado con préstamo o devolución de material', 30),
    ('COMPUTER_USE', 'Uso de equipo', 'Uso de computadoras u otros recursos tecnológicos', 40),
    ('MEETING', 'Reunión', 'Ingreso para reunión con personal o comunidad institucional', 50),
    ('EVENT', 'Evento', 'Ingreso por actividad, conferencia, taller o evento', 60),
    ('PROCEDURE', 'Trámite', 'Ingreso para realizar un trámite', 70),
    ('OTHER', 'Otro', 'Motivo no incluido en las categorías anteriores', 999);

-- Agrega aquí únicamente las carreras reales de tu institución.
-- Ejemplo:
--
-- INSERT INTO careers (code, name)
-- VALUES
--     ('ISC', 'Ingeniería en Sistemas Computacionales'),
--     ('IGE', 'Ingeniería en Gestión Empresarial');
