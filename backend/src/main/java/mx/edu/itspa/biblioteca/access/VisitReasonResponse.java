package mx.edu.itspa.biblioteca.access;

public record VisitReasonResponse(
        Long id,
        String code,
        String name,
        String description
) {
    public static VisitReasonResponse from(VisitReason reason) {
        return new VisitReasonResponse(
                reason.getId(),
                reason.getCode(),
                reason.getName(),
                reason.getDescription()
        );
    }
}
