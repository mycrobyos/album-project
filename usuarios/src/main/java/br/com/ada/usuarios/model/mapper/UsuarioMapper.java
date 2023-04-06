package br.com.ada.usuarios.model.mapper;

import br.com.ada.usuarios.model.dto.UsuarioDTO;
import br.com.ada.usuarios.model.dto.UsuarioUpdateDTO;
import br.com.ada.usuarios.model.entity.Usuario;
import br.com.ada.usuarios.model.dto.UsuarioCreationDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioDTO parseDTO(Usuario usuario);
    Usuario parseEntity(UsuarioDTO usuarioDTO);

    Usuario parseEntity(UsuarioCreationDTO usuarioDTO);
    Usuario parseEntity(UsuarioUpdateDTO usuarioDTO);
    List<UsuarioDTO> parseListDTO(List<Usuario> usuarios);

}
