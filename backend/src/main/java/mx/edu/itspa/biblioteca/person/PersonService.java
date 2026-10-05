package mx.edu.itspa.biblioteca.person;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PersonService {

    private static final String STUDENT = "STUDENT";
    private static final String VISITOR = "VISITOR";

    private final PersonTypeRepository personTypeRepository;
    private final PersonRepository personRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final CareerRepository careerRepository;

    public PersonService(
            PersonTypeRepository personTypeRepository,
            PersonRepository personRepository,
            StudentProfileRepository studentProfileRepository,
            CareerRepository careerRepository
    ) {
        this.personTypeRepository = personTypeRepository;
        this.personRepository = personRepository;
        this.studentProfileRepository = studentProfileRepository;
        this.careerRepository = careerRepository;
    }

    @Transactional(readOnly = true)
    public List<PersonTypeResponse> getActivePersonTypes() {
        return personTypeRepository
                .findByActiveTrueOrderByNameAsc()
                .stream()
                .map(PersonTypeResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CareerResponse> getActiveCareers() {
        return careerRepository
                .findByActiveTrueOrderByNameAsc()
                .stream()
                .map(CareerResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public PersonResponse findById(Long id) {
        Person person = findActivePerson(id);
        return toResponse(person);
    }

    @Transactional(readOnly = true)
    public PersonResponse findByInstitutionalIdentifier(String identifier) {
        String normalized = normalizeIdentifier(identifier);

        Person person = personRepository
                .findByInstitutionalIdentifier(normalized)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Persona no encontrada"
                ));

        ensurePersonAvailable(person);
        return toResponse(person);
    }

    @Transactional
    public PersonResponse createVisitor(CreateVisitorRequest request) {
        PersonType visitorType = personTypeRepository
                .findByCodeAndActiveTrue(VISITOR)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "El tipo de persona VISITOR no está configurado"
                ));

        Person visitor = new Person(
                visitorType,
                null,
                request.firstName(),
                request.paternalSurname(),
                request.maternalSurname(),
                request.email(),
                request.phone()
        );

        return PersonResponse.from(personRepository.save(visitor), null);
    }

    @Transactional(readOnly = true)
    public Person findActivePerson(Long id) {
        Person person = personRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Persona no encontrada"
                ));

        ensurePersonAvailable(person);
        return person;
    }

    private PersonResponse toResponse(Person person) {
        StudentProfile studentProfile = null;

        if (STUDENT.equals(person.getPersonType().getCode())) {
            studentProfile = studentProfileRepository
                    .findById(person.getId())
                    .orElse(null);
        }

        return PersonResponse.from(person, studentProfile);
    }

    private void ensurePersonAvailable(Person person) {
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

    private String normalizeIdentifier(String identifier) {
        if (identifier == null || identifier.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El identificador institucional es obligatorio"
            );
        }
        return identifier.trim();
    }
}
