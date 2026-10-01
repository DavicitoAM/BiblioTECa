package mx.edu.itspa.biblioteca.visit;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import mx.edu.itspa.biblioteca.student.Student;

@Entity
@Table(name = "visits")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "visit_reason_id", nullable = false)
    private VisitReason visitReason;

    @Column(name = "checked_in_at", nullable = false)
    private Instant checkedInAt;

    @Column(nullable = false)
    private String source;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected Visit() {
    }

    public Visit(
            Student student,
            VisitReason visitReason,
            Instant checkedInAt,
            String source
    ) {
        this.student = student;
        this.visitReason = visitReason;
        this.checkedInAt = checkedInAt;
        this.source = source;
        this.createdAt = checkedInAt;
    }

    public Long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public VisitReason getVisitReason() {
        return visitReason;
    }

    public Instant getCheckedInAt() {
        return checkedInAt;
    }

    public String getSource() {
        return source;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}