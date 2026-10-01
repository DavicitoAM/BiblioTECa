package mx.edu.itspa.biblioteca.student;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

   @Transactional(readOnly = true)
public StudentResponse findByControlNumber(String controlNumber) {

    Student student = studentRepository
            .findByControlNumber(controlNumber)
            .orElseThrow(() ->
                    new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Estudiante no encontrado"
                    )
            );

    if (!student.isActive()) {
        throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "El estudiante no se encuentra activo"
        );
    }

    return StudentResponse.from(student);
}
}