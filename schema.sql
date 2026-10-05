-- BiblioTECa ITSP - Esquema consolidado inicial
-- PostgreSQL

CREATE TABLE careers (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(150) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE students (
    id BIGSERIAL PRIMARY KEY,
    control_number VARCHAR(20) NOT NULL UNIQUE,
    first_name VARCHAR(100) NOT NULL,
    paternal_surname VARCHAR(100) NOT NULL,
    maternal_surname VARCHAR(100),
    career_id BIGINT NOT NULL,
    semester SMALLINT,
    academic_status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_students_career
        FOREIGN KEY (career_id) REFERENCES careers(id),

    CONSTRAINT chk_students_semester
        CHECK (semester IS NULL OR semester BETWEEN 1 AND 20),

    CONSTRAINT chk_students_academic_status
        CHECK (academic_status IN (
            'ACTIVE','INACTIVE','GRADUATED','TEMPORARY_LEAVE'
        ))
);

CREATE TABLE visit_reasons (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    display_order INTEGER NOT NULL DEFAULT 0,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE visits (
    id BIGSERIAL PRIMARY KEY,
    student_id BIGINT NOT NULL,
    visit_reason_id BIGINT NOT NULL,
    checked_in_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    source VARCHAR(30) NOT NULL DEFAULT 'KIOSK',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_visits_student
        FOREIGN KEY (student_id) REFERENCES students(id),

    CONSTRAINT fk_visits_reason
        FOREIGN KEY (visit_reason_id) REFERENCES visit_reasons(id),

    CONSTRAINT chk_visits_source
        CHECK (source IN ('KIOSK','STAFF','SYSTEM'))
);

CREATE INDEX idx_students_career
    ON students(career_id);

CREATE INDEX idx_visits_student
    ON visits(student_id);

CREATE INDEX idx_visits_reason
    ON visits(visit_reason_id);

CREATE INDEX idx_visits_checked_in_at
    ON visits(checked_in_at DESC);
