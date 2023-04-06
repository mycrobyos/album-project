package br.com.ada.usuarios.service;

import br.com.ada.usuarios.model.dto.UsuarioCreationDTO;
import br.com.ada.usuarios.model.dto.UsuarioDTO;
import br.com.ada.usuarios.model.dto.UsuarioUpdateDTO;
import br.com.ada.usuarios.model.entity.Phone;

import java.util.List;

public interface UsuarioService {

    List<UsuarioDTO> findAll();
    UsuarioDTO findById(String id);
    UsuarioDTO create(UsuarioCreationDTO entity);
    UsuarioDTO edit(String id, UsuarioUpdateDTO entity);
    void delete(String id);
    List<Phone> findPhones(String id);
}
