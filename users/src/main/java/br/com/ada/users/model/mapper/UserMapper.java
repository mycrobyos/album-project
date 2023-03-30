package br.com.ada.users.model.mapper;

import br.com.ada.users.model.dto.UserDTO;
import br.com.ada.users.model.dto.UserUpdateDTO;
import br.com.ada.users.model.entity.User;
import br.com.ada.users.model.dto.UserCreationDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO parseDTO(User user);
    User parseEntity(UserDTO userDTO);

    User parseEntity(UserCreationDTO userDTO);
    User parseEntity(UserUpdateDTO userDTO);
    List<UserDTO> parseListDTO(List<User> users);

}
