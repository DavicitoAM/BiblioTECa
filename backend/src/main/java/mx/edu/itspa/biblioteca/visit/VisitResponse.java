package mx.edu.itspa.biblioteca.visit;

import java.time.Instant;

public record VisitResponse(

        Long id,

        StudentSummary student,

        VisitReasonSummary reason,

        Instant checkedInAt,

        String source

) {

    public record StudentSummary(
            String controlNumber,
            String fullName,
            String career,
            Short semester
    ) {
    }

    public record VisitReasonSummary(
            Long id,
            String code,
            String name
    ) {
    }

    public static VisitResponse from(Visit visit) {

        return new VisitResponse(

                visit.getId(),

                new StudentSummary(
                        visit.getStudent().getControlNumber(),
                        visit.getStudent().getFullName(),
                        visit.getStudent().getCareer().getName(),
                        visit.getStudent().getSemester()
                ),

                new VisitReasonSummary(
                        visit.getVisitReason().getId(),
                        visit.getVisitReason().getCode(),
                        visit.getVisitReason().getName()
                ),

                visit.getCheckedInAt(),

                visit.getSource()
        );
    }
}