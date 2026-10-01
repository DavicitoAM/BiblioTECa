-- Datos ficticios de desarrollo

INSERT INTO careers (code, name)
VALUES
('ISC', 'Ingeniería en Sistemas Computacionales'),
('ITIC', 'Ingeniería en Tecnologías de la Información y Comunicaciones'),
('IGE', 'Ingeniería en Gestión Empresarial'),
('II', 'Ingeniería Industrial');

INSERT INTO students (
    control_number, first_name, paternal_surname, maternal_surname,
    career_id, semester, academic_status
)
VALUES
(
    '22123456','Andrea','García','López',
    (SELECT id FROM careers WHERE code = 'ISC'),6,'ACTIVE'
),
(
    '22123457','Carlos','Martínez','Rodríguez',
    (SELECT id FROM careers WHERE code = 'II'),4,'ACTIVE'
),
(
    '22123458','Sofía','Hernández','Pérez',
    (SELECT id FROM careers WHERE code = 'ITIC'),8,'ACTIVE'
);

INSERT INTO visit_reasons (code, name, description, display_order)
VALUES
('STUDY','Estudio individual','Uso de la biblioteca como espacio de estudio individual.',1),
('MATERIAL_QUERY','Consulta de material','Consulta de libros u otros recursos bibliográficos.',2),
('COMPUTER','Uso de computadora','Uso de los equipos de cómputo disponibles.',3),
('TEAM_WORK','Trabajo en equipo','Actividades académicas realizadas en grupo.',4),
('CUBICLE','Uso de cubículo','Uso de cubículos o espacios de trabajo.',5),
('LOAN_RETURN','Préstamo o devolución','Préstamo, renovación o devolución de material.',6),
('DIGITAL_SERVICES','Servicios digitales','Consulta de bases de datos o recursos digitales.',7),
('OTHER','Otro servicio bibliotecario','Otro servicio disponible dentro de la biblioteca.',8);
