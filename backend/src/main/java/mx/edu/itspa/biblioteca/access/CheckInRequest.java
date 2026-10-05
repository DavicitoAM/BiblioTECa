package mx.edu.itspa.biblioteca.access;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CheckInRequest(

        @NotNull(message = "La persona es obligatoria")
        @Positive(message = "La persona debe ser válida")
        Long personId,

        @Positive(message = "El motivo de visita debe ser válido")
        Long visitReasonId,

        @Size(max = 150, message = "El destino no puede exceder 150 caracteres")
        String destination,

        @Size(max = 500, message = "Las observaciones no pueden exceder 500 caracteres")
        String notes
) {
}
