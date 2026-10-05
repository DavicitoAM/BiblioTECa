package mx.edu.itspa.biblioteca.shared;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SystemController {

    @GetMapping("/")
    public Map<String, Object> root() {
        return Map.of(
                "application", "BiblioTECa",
                "status", "running",
                "api", "/api/v1",
                "health", "/actuator/health"
        );
    }
}