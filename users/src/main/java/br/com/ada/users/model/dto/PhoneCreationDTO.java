package br.com.ada.users.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PhoneCreationDTO {
    @NotBlank(message = "CountryCode field is missing or blank.")
    private String countryCode;

    @NotBlank(message = "AreaCode field is missing or blank.")
    private String areaCode;

    @NotBlank(message = "PhoneNumber field is missing or blank.")
    private String phoneNumber;

    @NotNull(message = "User field is missing or blank.")
    private UserDTO user;
}
