package mx.edu.itspa.biblioteca.person;

public record PersonResponse(
        Long id,
        PersonTypeSummary type,
        String institutionalIdentifier,
        String fullName,
        String email,
        String phone,
        StudentDetails student,
        boolean active
) {

    public record PersonTypeSummary(
            Long id,
            String code,
            String name
    ) {
    }

    public record StudentDetails(
            Long careerId,
            String careerCode,
            String careerName,
            Short semester,
            String academicStatus
    ) {
    }

    public static PersonResponse from(
            Person person,
            StudentProfile studentProfile
    ) {
        StudentDetails student = null;

        if (studentProfile != null) {
            Career career = studentProfile.getCareer();
            student = new StudentDetails(
                    career != null ? career.getId() : null,
                    career != null ? career.getCode() : null,
                    career != null ? career.getName() : null,
                    studentProfile.getSemester(),
                    studentProfile.getAcademicStatus()
            );
        }

        return new PersonResponse(
                person.getId(),
                new PersonTypeSummary(
                        person.getPersonType().getId(),
                        person.getPersonType().getCode(),
                        person.getPersonType().getName()
                ),
                person.getInstitutionalIdentifier(),
                person.getFullName(),
                person.getEmail(),
                person.getPhone(),
                student,
                person.isActive()
        );
    }
}
