package br.com.ada.users.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class AddressCreationDTO {
    @NotBlank(message = "Street field is missing or blank.")
    private String street;

    @NotBlank(message = "Number field is missing or blank.")
    private String number;

    @NotBlank(message = "Neighborhood field is missing or blank.")
    private String neighborhood;

    private String complement;

    @NotBlank(message = "City field is missing or blank.")
    private String city;

    @NotBlank(message = "State field is missing or blank.")
    @Pattern(regexp="\\d{2}")
    private String state;

    @NotBlank(message = "Zipcode field is missing or blank.")
    @Pattern(regexp="\\d{8}")
    private String zipCode;
}
