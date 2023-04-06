package br.com.ada.usuarios.service.impl;

import br.com.ada.usuarios.model.dto.UsuarioCreationDTO;
import br.com.ada.usuarios.model.dto.UsuarioDTO;
import br.com.ada.usuarios.model.dto.UsuarioUpdateDTO;
import br.com.ada.usuarios.model.entity.Phone;
import br.com.ada.usuarios.model.entity.Usuario;
import br.com.ada.usuarios.model.mapper.UsuarioMapper;
import br.com.ada.usuarios.repository.UsuarioRepository;
import br.com.ada.usuarios.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;

    public UsuarioServiceImpl(UsuarioRepository repository, UsuarioMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    
    @Override
    public List<UsuarioDTO> findAll() {
        return mapper.parseListDTO(repository.findAll());
    }

    @Override
    public UsuarioDTO findById(String id) {
        Optional<Usuario> usuarioOptional = repository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            return mapper.parseDTO(usuario);
        }
        throw new EntityNotFoundException();
    }

    @Override
    public UsuarioDTO create(UsuarioCreationDTO entity) {
        Usuario usuario = mapper.parseEntity(entity);
        usuario.setId(null);
        repository.save(usuario);
        return mapper.parseDTO(usuario);
    }

    @Override
    public UsuarioDTO edit(String id, UsuarioUpdateDTO entity) {
        Optional<Usuario> optional = repository.findById(id);
        if (optional.isPresent()) {
            Usuario usuario = mapper.parseEntity(entity);
            usuario.setId(id);
            usuario.setAddress(optional.get().getAddress());
            usuario = repository.save(usuario);
            return mapper.parseDTO(usuario);
        }
        throw new EntityNotFoundException();
    }

    @Override
    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException();
        }
        repository.deleteById(id);
    }

    @Override
    public List<Phone> findPhones(String id) {
        return repository.findPhones(id);
    }
}
