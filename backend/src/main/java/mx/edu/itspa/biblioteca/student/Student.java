package mx.edu.itspa.biblioteca.student;
import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "control_number", nullable = false, unique = true)
    private String controlNumber;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "paternal_surname", nullable = false)
    private String paternalSurname;

    @Column(name = "maternal_surname")
    private String maternalSurname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "career_id", nullable = false)
    private Career career;

    private Short semester;

    @Column(name = "academic_status", nullable = false)
    private String academicStatus;

    @Column(nullable = false)
    private boolean active;

    public Long getId() {
        return id;
    }

    public String getControlNumber() {
        return controlNumber;
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

    public Career getCareer() {
        return career;
    }

    public Short getSemester() {
        return semester;
    }

    public String getAcademicStatus() {
        return academicStatus;
    }

    public boolean isActive() {
        return active;
    }

    public String getFullName() {
        return String.join(
            " ",
            firstName,
            paternalSurname,
            maternalSurname == null ? "" : maternalSurname
        ).trim();
    }
}
