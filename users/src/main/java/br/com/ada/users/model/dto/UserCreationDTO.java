package br.com.ada.users.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCreationDTO {

    @NotBlank(message = "Name field is missing or blank.")
    private String name;

    @NotBlank(message = "Document field is missing or blank.")
    private String document;

    @Email(message = "Email field is invalid.")
    @NotBlank(message = "Email field is missing or blank.")
    private String email;
    
    @NotNull(message = "At least one address must be informed.")
    private AddressCreationDTO address;

//    @NotNull(message = "At least one phone must be informed.")
//    private List<PhoneCreationDTO> phones;

}
