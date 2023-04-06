package br.com.ada.usuarios.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioDTO {

    private String id;

    private String name;

    private String document;

    private String email;

    private AddressDTO address;

//    private List<Phone> phones;

}
