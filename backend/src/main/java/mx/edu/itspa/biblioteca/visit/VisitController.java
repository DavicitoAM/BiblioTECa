package mx.edu.itspa.biblioteca.visit;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class VisitController {

    private final VisitService visitService;

    public VisitController(
            VisitService visitService
    ) {
        this.visitService = visitService;
    }

    @GetMapping("/visit-reasons")
    public List<VisitReasonResponse> getVisitReasons() {

        return visitService.getActiveReasons();
    }

    @PostMapping("/visits")
    @ResponseStatus(HttpStatus.CREATED)
    public VisitResponse createVisit(

            @Valid
            @RequestBody
            CreateVisitRequest request

    ) {

        return visitService.createVisit(request);
    }
}