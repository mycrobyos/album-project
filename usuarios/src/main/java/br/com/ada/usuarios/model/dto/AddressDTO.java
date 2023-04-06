package br.com.ada.usuarios.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressDTO {

    private String id;

    private String street;

    private String number;

    private String neighborhood;

    private String complement;

    private String city;

    private String state;

    private String zipCode;
}
