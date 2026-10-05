package mx.edu.itspa.biblioteca.access;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import mx.edu.itspa.biblioteca.auth.SystemUser;
import mx.edu.itspa.biblioteca.person.Person;

@Entity
@Table(name = "access_records")
public class AccessRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_reason_id")
    private VisitReason visitReason;

    @Column(name = "checked_in_at", nullable = false)
    private Instant checkedInAt;

    @Column(name = "checked_out_at")
    private Instant checkedOutAt;

    @Column(length = 150)
    private String destination;

    @Column(length = 500)
    private String notes;

    /*
     * Canal/dispositivo que originó la operación.
     * Ejemplos actuales: KIOSK, WEB, ADMIN.
     */
    @Column(nullable = false, length = 30)
    private String source;

    /*
     * Quién realizó la captura desde el punto de vista funcional.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "capture_mode", nullable = false, length = 20)
    private CaptureMode captureMode;

    /*
     * Sólo existe cuando un usuario interno capturó el acceso.
     * En SELF_SERVICE permanece NULL.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registered_by_user_id")
    private SystemUser registeredBy;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected AccessRecord() {
    }

    /*
     * Constructor utilizado por el flujo público/kiosco actual.
     * Se conserva para que el frontend existente siga funcionando.
     */
    public AccessRecord(
            Person person,
            VisitReason visitReason,
            String destination,
            String notes,
            String source,
            Instant checkedInAt
    ) {
        this(
                person,
                visitReason,
                destination,
                notes,
                source,
                CaptureMode.SELF_SERVICE,
                null,
                checkedInAt
        );
    }

    /*
     * Constructor preparado para recepción y administración.
     */
    public AccessRecord(
            Person person,
            VisitReason visitReason,
            String destination,
            String notes,
            String source,
            CaptureMode captureMode,
            SystemUser registeredBy,
            Instant checkedInAt
    ) {
        this.person = person;
        this.visitReason = visitReason;
        this.destination = normalizeNullable(destination);
        this.notes = normalizeNullable(notes);
        this.source = normalizeRequired(source);
        this.captureMode = captureMode;
        this.registeredBy = registeredBy;
        this.checkedInAt = checkedInAt;
    }

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();

        if (checkedInAt == null) {
            checkedInAt = now;
        }

        if (captureMode == null) {
            captureMode = CaptureMode.SELF_SERVICE;
        }

        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }

    public void checkOut(Instant when) {
        if (checkedOutAt != null) {
            throw new IllegalStateException("El acceso ya fue cerrado");
        }
        this.checkedOutAt = when;
    }

    public Long getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

    public VisitReason getVisitReason() {
        return visitReason;
    }

    public Instant getCheckedInAt() {
        return checkedInAt;
    }

    public Instant getCheckedOutAt() {
        return checkedOutAt;
    }

    public String getDestination() {
        return destination;
    }

    public String getNotes() {
        return notes;
    }

    public String getSource() {
        return source;
    }

    public CaptureMode getCaptureMode() {
        return captureMode;
    }

    public SystemUser getRegisteredBy() {
        return registeredBy;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
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
}
