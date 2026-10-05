package mx.edu.itspa.biblioteca.person;

public record PersonTypeResponse(
        Long id,
        String code,
        String name,
        String description
) {
    public static PersonTypeResponse from(PersonType type) {
        return new PersonTypeResponse(
                type.getId(),
                type.getCode(),
                type.getName(),
                type.getDescription()
        );
    }
}
