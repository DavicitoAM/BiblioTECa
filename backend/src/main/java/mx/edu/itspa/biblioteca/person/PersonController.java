package mx.edu.itspa.biblioteca.person;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/person-types")
    public List<PersonTypeResponse> getPersonTypes() {
        return personService.getActivePersonTypes();
    }

    @GetMapping("/careers")
    public List<CareerResponse> getCareers() {
        return personService.getActiveCareers();
    }

    @GetMapping("/persons/{id}")
    public PersonResponse getPerson(@PathVariable Long id) {
        return personService.findById(id);
    }

    @GetMapping("/persons/identifier/{identifier}")
    public PersonResponse findByInstitutionalIdentifier(
            @PathVariable String identifier
    ) {
        return personService.findByInstitutionalIdentifier(identifier);
    }

    @PostMapping("/persons/visitors")
    @ResponseStatus(HttpStatus.CREATED)
    public PersonResponse createVisitor(
            @Valid @RequestBody CreateVisitorRequest request
    ) {
        return personService.createVisitor(request);
    }
}
