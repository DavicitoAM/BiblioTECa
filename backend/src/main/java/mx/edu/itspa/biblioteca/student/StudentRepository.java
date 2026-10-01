package mx.edu.itspa.biblioteca.student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository
        extends JpaRepository<Student, Long> {

    Optional<Student> findByControlNumber(String controlNumber);
}