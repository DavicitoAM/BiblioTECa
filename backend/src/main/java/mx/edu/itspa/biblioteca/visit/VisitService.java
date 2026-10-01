package mx.edu.itspa.biblioteca.visit;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import mx.edu.itspa.biblioteca.student.Student;
import mx.edu.itspa.biblioteca.student.StudentRepository;

@Service
public class VisitService {

    private final VisitRepository visitRepository;
    private final VisitReasonRepository visitReasonRepository;
    private final StudentRepository studentRepository;

    public VisitService(
            VisitRepository visitRepository,
            VisitReasonRepository visitReasonRepository,
            StudentRepository studentRepository
    ) {
        this.visitRepository = visitRepository;
        this.visitReasonRepository = visitReasonRepository;
        this.studentRepository = studentRepository;
    }

    @Transactional(readOnly = true)
    public List<VisitReasonResponse> getActiveReasons() {

        return visitReasonRepository
                .findByActiveTrueOrderByDisplayOrderAscNameAsc()
                .stream()
                .map(VisitReasonResponse::from)
                .toList();
    }

    @Transactional
    public VisitResponse createVisit(
            CreateVisitRequest request
    ) {

        String controlNumber =
                request.controlNumber().trim();

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

        VisitReason reason = visitReasonRepository
                .findById(request.visitReasonId())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Motivo de visita no encontrado"
                        )
                );

        if (!reason.isActive()) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El motivo de visita no está disponible"
            );
        }

        Instant now = Instant.now();

        Visit visit = new Visit(
                student,
                reason,
                now,
                "KIOSK"
        );

        Visit savedVisit =
                visitRepository.save(visit);

        return VisitResponse.from(savedVisit);
    }
}