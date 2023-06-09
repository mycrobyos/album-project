package br.com.ada.usuarios.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioUpdateDTO {

    @NotBlank(message = "Name field is missing or blank.")
    private String name;

    @NotBlank(message = "Document field is missing or blank.")
    private String document;

    @Email(message = "Email field is invalid.")
    @NotBlank(message = "Email field is missing or blank.")
    private String email;
    
//    @NotNull(message = "At least one address must be informed.")
//    private AddressCreationDTO address;

}
