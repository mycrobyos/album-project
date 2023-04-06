package br.com.ada.usuarios.service;

import br.com.ada.usuarios.model.dto.PhoneCreationDTO;
import br.com.ada.usuarios.model.dto.PhoneDTO;
import br.com.ada.usuarios.model.dto.PhoneUpdateDTO;

import java.util.List;

public interface PhoneService {
    List<PhoneDTO> findAll();
    PhoneDTO findById(String id);
    PhoneDTO create(PhoneCreationDTO entity);
    PhoneDTO edit(String id, PhoneUpdateDTO entity);
    void delete(String id);
}
