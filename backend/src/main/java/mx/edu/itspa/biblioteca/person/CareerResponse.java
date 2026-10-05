package mx.edu.itspa.biblioteca.person;

public record CareerResponse(
        Long id,
        String code,
        String name
) {
    public static CareerResponse from(Career career) {
        return new CareerResponse(
                career.getId(),
                career.getCode(),
                career.getName()
        );
    }
}
