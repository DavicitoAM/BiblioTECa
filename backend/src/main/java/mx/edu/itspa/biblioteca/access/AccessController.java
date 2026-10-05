package mx.edu.itspa.biblioteca.access;

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
public class AccessController {

    private final AccessService accessService;

    public AccessController(AccessService accessService) {
        this.accessService = accessService;
    }

    @GetMapping("/visit-reasons")
    public List<VisitReasonResponse> getVisitReasons() {
        return accessService.getActiveReasons();
    }

    @GetMapping("/access-records/open")
    public List<AccessRecordResponse> getOpenAccesses() {
        return accessService.getOpenAccesses();
    }

    @GetMapping("/access-records/recent")
    public List<AccessRecordResponse> getRecentAccesses() {
        return accessService.getRecentAccesses();
    }

    @PostMapping("/access-records/check-in")
    @ResponseStatus(HttpStatus.CREATED)
    public AccessRecordResponse checkIn(
            @Valid @RequestBody CheckInRequest request
    ) {
        return accessService.checkIn(request);
    }

    @PostMapping("/access-records/{accessRecordId}/check-out")
    public AccessRecordResponse checkOut(
            @PathVariable Long accessRecordId
    ) {
        return accessService.checkOut(accessRecordId);
    }
}
