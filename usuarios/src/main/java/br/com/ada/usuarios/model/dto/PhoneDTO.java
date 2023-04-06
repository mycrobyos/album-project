package br.com.ada.usuarios.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PhoneDTO {
    private String id;

    private String countryCode;

    private String areaCode;

    private String phoneNumber;

    private UsuarioDTO usuario;
}
