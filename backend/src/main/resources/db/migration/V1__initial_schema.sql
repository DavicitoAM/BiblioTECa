-- ============================================================
-- BiblioTECa - V1__initial_schema.sql
-- Modelo v2: acceso generalizado por PERSONA
-- PostgreSQL + Flyway
-- ============================================================

CREATE TABLE person_types (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(30) NOT NULL UNIQUE,
    name VARCHAR(80) NOT NULL,
    description VARCHAR(255),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE careers (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(150) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
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

CREATE TABLE persons (
    id BIGSERIAL PRIMARY KEY,
    person_type_id BIGINT NOT NULL,
    institutional_identifier VARCHAR(30),
    first_name VARCHAR(100) NOT NULL,
    paternal_surname VARCHAR(100) NOT NULL,
    maternal_surname VARCHAR(100),
    email VARCHAR(150),
    phone VARCHAR(30),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_persons_person_type
        FOREIGN KEY (person_type_id)
        REFERENCES person_types(id)
        ON DELETE RESTRICT,

    CONSTRAINT uq_persons_institutional_identifier
        UNIQUE (institutional_identifier)
);

CREATE TABLE student_profiles (
    person_id BIGINT PRIMARY KEY,
    career_id BIGINT,
    semester SMALLINT,
    academic_status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',

    CONSTRAINT fk_student_profiles_person
        FOREIGN KEY (person_id)
        REFERENCES persons(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_student_profiles_career
        FOREIGN KEY (career_id)
        REFERENCES careers(id)
        ON DELETE RESTRICT,

    CONSTRAINT chk_student_profiles_semester
        CHECK (
            semester IS NULL
            OR semester BETWEEN 1 AND 20
        )
);

CREATE TABLE access_records (
    id BIGSERIAL PRIMARY KEY,
    person_id BIGINT NOT NULL,
    visit_reason_id BIGINT,
    checked_in_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    checked_out_at TIMESTAMPTZ,
    destination VARCHAR(150),
    notes VARCHAR(500),
    source VARCHAR(30) NOT NULL DEFAULT 'MANUAL',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_access_records_person
        FOREIGN KEY (person_id)
        REFERENCES persons(id)
        ON DELETE RESTRICT,

    CONSTRAINT fk_access_records_visit_reason
        FOREIGN KEY (visit_reason_id)
        REFERENCES visit_reasons(id)
        ON DELETE RESTRICT,

    CONSTRAINT chk_access_records_dates
        CHECK (
            checked_out_at IS NULL
            OR checked_out_at >= checked_in_at
        )
);

CREATE INDEX idx_persons_person_type
    ON persons(person_type_id);

CREATE INDEX idx_persons_name
    ON persons(paternal_surname, maternal_surname, first_name);

CREATE INDEX idx_student_profiles_career
    ON student_profiles(career_id);

CREATE INDEX idx_access_records_person
    ON access_records(person_id);

CREATE INDEX idx_access_records_checked_in_at
    ON access_records(checked_in_at DESC);

CREATE INDEX idx_access_records_visit_reason
    ON access_records(visit_reason_id);

CREATE UNIQUE INDEX uq_access_records_one_open_per_person
    ON access_records(person_id)
    WHERE checked_out_at IS NULL;
