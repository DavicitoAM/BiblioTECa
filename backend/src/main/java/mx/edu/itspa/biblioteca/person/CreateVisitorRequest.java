package mx.edu.itspa.biblioteca.person;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateVisitorRequest(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
        String firstName,

        @NotBlank(message = "El apellido paterno es obligatorio")
        @Size(max = 100, message = "El apellido paterno no puede exceder 100 caracteres")
        String paternalSurname,

        @Size(max = 100, message = "El apellido materno no puede exceder 100 caracteres")
        String maternalSurname,

        @Email(message = "El correo no tiene un formato válido")
        @Size(max = 150, message = "El correo no puede exceder 150 caracteres")
        String email,

        @Size(max = 30, message = "El teléfono no puede exceder 30 caracteres")
        String phone
) {
}
