package mx.edu.itspa.biblioteca.access;

import java.time.Instant;

import mx.edu.itspa.biblioteca.auth.SystemUser;
import mx.edu.itspa.biblioteca.person.Person;

public record AccessRecordResponse(
        Long id,
        PersonSummary person,
        VisitReasonSummary reason,
        Instant checkedInAt,
        Instant checkedOutAt,
        String destination,
        String notes,
        String source,
        String captureMode,
        OperatorSummary registeredBy,
        boolean open
) {

    public record PersonSummary(
            Long id,
            String typeCode,
            String typeName,
            String institutionalIdentifier,
            String fullName
    ) {
    }

    public record VisitReasonSummary(
            Long id,
            String code,
            String name
    ) {
    }

    public record OperatorSummary(
            Long id,
            String displayName,
            String role
    ) {
    }

    public static AccessRecordResponse from(AccessRecord access) {
        Person person = access.getPerson();
        VisitReason reason = access.getVisitReason();
        SystemUser operator = access.getRegisteredBy();

        return new AccessRecordResponse(
                access.getId(),
                new PersonSummary(
                        person.getId(),
                        person.getPersonType().getCode(),
                        person.getPersonType().getName(),
                        person.getInstitutionalIdentifier(),
                        person.getFullName()
                ),
                reason == null
                        ? null
                        : new VisitReasonSummary(
                                reason.getId(),
                                reason.getCode(),
                                reason.getName()
                        ),
                access.getCheckedInAt(),
                access.getCheckedOutAt(),
                access.getDestination(),
                access.getNotes(),
                access.getSource(),
                access.getCaptureMode().name(),
                operator == null
                        ? null
                        : new OperatorSummary(
                                operator.getId(),
                                operator.getDisplayName(),
                                operator.getRole().name()
                        ),
                access.getCheckedOutAt() == null
        );
    }
}
