package mx.edu.itspa.biblioteca.student;
public record StudentResponse(
    Long id,
    String controlNumber,
    String fullName,
    String career,
    Short semester,
    String academicStatus
) {

    public static StudentResponse from(Student student) {
        return new StudentResponse(
            student.getId(),
            student.getControlNumber(),
            student.getFullName(),
            student.getCareer().getName(),
            student.getSemester(),
            student.getAcademicStatus()
        );
    }
}