package mx.edu.itspa.biblioteca.visit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateVisitRequest(

        @NotBlank(message = "El número de control es obligatorio")
        @Size(
                max = 20,
                message = "El número de control no puede exceder 20 caracteres"
        )
        String controlNumber,

        @NotNull(message = "El motivo de visita es obligatorio")
        @Positive(message = "El motivo de visita debe ser válido")
        Long visitReasonId

) {
}