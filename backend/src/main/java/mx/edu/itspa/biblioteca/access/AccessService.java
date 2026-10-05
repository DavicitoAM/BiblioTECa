package mx.edu.itspa.biblioteca.access;

import java.time.Instant;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import mx.edu.itspa.biblioteca.person.Person;
import mx.edu.itspa.biblioteca.person.PersonRepository;

@Service
public class AccessService {

    private static final String VISITOR = "VISITOR";
    private static final String DEFAULT_SOURCE = "KIOSK";

    private final AccessRecordRepository accessRecordRepository;
    private final VisitReasonRepository visitReasonRepository;
    private final PersonRepository personRepository;

    public AccessService(
            AccessRecordRepository accessRecordRepository,
            VisitReasonRepository visitReasonRepository,
            PersonRepository personRepository
    ) {
        this.accessRecordRepository = accessRecordRepository;
        this.visitReasonRepository = visitReasonRepository;
        this.personRepository = personRepository;
    }

    @Transactional(readOnly = true)
    public List<VisitReasonResponse> getActiveReasons() {
        return visitReasonRepository
                .findByActiveTrueOrderByDisplayOrderAscNameAsc()
                .stream()
                .map(VisitReasonResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AccessRecordResponse> getOpenAccesses() {
        return accessRecordRepository
                .findByCheckedOutAtIsNullOrderByCheckedInAtDesc()
                .stream()
                .map(AccessRecordResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AccessRecordResponse> getRecentAccesses() {
        return accessRecordRepository
                .findTop100ByOrderByCheckedInAtDesc()
                .stream()
                .map(AccessRecordResponse::from)
                .toList();
    }

    @Transactional
    public AccessRecordResponse checkIn(CheckInRequest request) {
        Person person = personRepository
                .findById(request.personId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Persona no encontrada"
                ));

        ensurePersonCanEnter(person);

        if (accessRecordRepository.existsByPersonIdAndCheckedOutAtIsNull(person.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "La persona ya tiene una entrada abierta"
            );
        }

        VisitReason reason = resolveReason(request.visitReasonId());
        validateConditionalFields(person, reason, request.destination());

        AccessRecord access = new AccessRecord(
                person,
                reason,
                request.destination(),
                request.notes(),
                DEFAULT_SOURCE,
                Instant.now()
        );

        try {
            AccessRecord saved = accessRecordRepository.saveAndFlush(access);
            return AccessRecordResponse.from(saved);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No fue posible registrar la entrada. Verifica que la persona no tenga ya un acceso abierto."
            );
        }
    }

    @Transactional
    public AccessRecordResponse checkOut(Long accessRecordId) {
        AccessRecord access = accessRecordRepository
                .findByIdAndCheckedOutAtIsNull(accessRecordId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No existe un acceso abierto con ese identificador"
                ));

        access.checkOut(Instant.now());
        return AccessRecordResponse.from(accessRecordRepository.save(access));
    }

    private VisitReason resolveReason(Long visitReasonId) {
        if (visitReasonId == null) {
            return null;
        }

        VisitReason reason = visitReasonRepository
                .findById(visitReasonId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Motivo de visita no encontrado"
                ));

        if (!reason.isActive()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El motivo de visita no está disponible"
            );
        }

        return reason;
    }

    private void ensurePersonCanEnter(Person person) {
        if (!person.isActive()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "La persona no se encuentra activa"
            );
        }

        if (!person.getPersonType().isActive()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El tipo de persona no está disponible"
            );
        }
    }

    private void validateConditionalFields(
            Person person,
            VisitReason reason,
            String destination
    ) {
        if (!VISITOR.equals(person.getPersonType().getCode())) {
            return;
        }

        if (reason == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El motivo de visita es obligatorio para visitantes"
            );
        }

        if (destination == null || destination.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El destino es obligatorio para visitantes"
            );
        }
    }
}
