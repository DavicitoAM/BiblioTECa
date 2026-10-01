package mx.edu.itspa.biblioteca.student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/control-number/{controlNumber}")
    public StudentResponse findByControlNumber(
            @PathVariable String controlNumber
    ) {
        return studentService.findByControlNumber(controlNumber);
    }
}