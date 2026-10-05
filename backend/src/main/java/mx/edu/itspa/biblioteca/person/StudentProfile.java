package mx.edu.itspa.biblioteca.person;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "student_profiles")
public class StudentProfile {

    @Id
    @Column(name = "person_id")
    private Long personId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "career_id")
    private Career career;

    private Short semester;

    @Column(name = "academic_status", nullable = false, length = 30)
    private String academicStatus;

    protected StudentProfile() {
    }

    public Long getPersonId() {
        return personId;
    }

    public Person getPerson() {
        return person;
    }

    public Career getCareer() {
        return career;
    }

    public Short getSemester() {
        return semester;
    }

    public String getAcademicStatus() {
        return academicStatus;
    }
}
