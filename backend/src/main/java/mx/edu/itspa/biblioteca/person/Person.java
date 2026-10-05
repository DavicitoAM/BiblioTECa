package mx.edu.itspa.biblioteca.person;

import java.time.Instant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "persons")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_type_id", nullable = false)
    private PersonType personType;

    @Column(name = "institutional_identifier", unique = true, length = 30)
    private String institutionalIdentifier;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "paternal_surname", nullable = false, length = 100)
    private String paternalSurname;

    @Column(name = "maternal_surname", length = 100)
    private String maternalSurname;

    @Column(length = 150)
    private String email;

    @Column(length = 30)
    private String phone;

    @Column(nullable = false)
    private boolean active = true;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    protected Person() {
    }

    public Person(
            PersonType personType,
            String institutionalIdentifier,
            String firstName,
            String paternalSurname,
            String maternalSurname,
            String email,
            String phone
    ) {
        this.personType = personType;
        this.institutionalIdentifier = normalizeNullable(institutionalIdentifier);
        this.firstName = normalizeRequired(firstName);
        this.paternalSurname = normalizeRequired(paternalSurname);
        this.maternalSurname = normalizeNullable(maternalSurname);
        this.email = normalizeNullable(email);
        this.phone = normalizeNullable(phone);
        this.active = true;
    }

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = Instant.now();
    }

    private static String normalizeRequired(String value) {
        return value == null ? null : value.trim();
    }

    private static String normalizeNullable(String value) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim();
        return normalized.isEmpty() ? null : normalized;
    }

    public Long getId() {
        return id;
    }

    public PersonType getPersonType() {
        return personType;
    }

    public String getInstitutionalIdentifier() {
        return institutionalIdentifier;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPaternalSurname() {
        return paternalSurname;
    }

    public String getMaternalSurname() {
        return maternalSurname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public String getFullName() {
        StringBuilder name = new StringBuilder(firstName);
        name.append(" ").append(paternalSurname);
        if (maternalSurname != null && !maternalSurname.isBlank()) {
            name.append(" ").append(maternalSurname);
        }
        return name.toString();
    }
}
