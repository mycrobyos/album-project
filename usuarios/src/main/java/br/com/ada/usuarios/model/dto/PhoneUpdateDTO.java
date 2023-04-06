package br.com.ada.usuarios.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PhoneUpdateDTO {
    @NotBlank(message = "CountryCode field is missing or blank.")
    private String countryCode;

    @NotBlank(message = "AreaCode field is missing or blank.")
    private String areaCode;

    @NotBlank(message = "PhoneNumber field is missing or blank.")
    private String phoneNumber;

    @NotNull(message = "Usuario field is missing or blank.")
    private UsuarioDTO usuario;
}
