package br.com.ada.users.service;

import br.com.ada.users.model.dto.UserCreationDTO;
import br.com.ada.users.model.dto.UserDTO;
import br.com.ada.users.model.dto.UserUpdateDTO;
import br.com.ada.users.model.entity.Phone;

import java.util.List;

public interface UserService {

    List<UserDTO> findAll();
    UserDTO findById(String id);
    UserDTO create(UserCreationDTO entity);
    UserDTO edit(String id, UserUpdateDTO entity);
    void delete(String id);
    List<Phone> findPhones(String id);
}
