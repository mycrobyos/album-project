package br.com.ada.users.service.impl;

import br.com.ada.users.model.dto.UserCreationDTO;
import br.com.ada.users.model.dto.UserDTO;
import br.com.ada.users.model.dto.UserUpdateDTO;
import br.com.ada.users.model.entity.Phone;
import br.com.ada.users.model.entity.User;
import br.com.ada.users.model.mapper.UserMapper;
import br.com.ada.users.repository.UserRepository;
import br.com.ada.users.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public UserServiceImpl(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    
    @Override
    public List<UserDTO> findAll() {
        return mapper.parseListDTO(repository.findAll());
    }

    @Override
    public UserDTO findById(String id) {
        Optional<User> userOptional = repository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return mapper.parseDTO(user);
        }
        throw new EntityNotFoundException();
    }

    @Override
    public UserDTO create(UserCreationDTO entity) {
        User user = mapper.parseEntity(entity);
        user.setId(null);
        repository.save(user);
        return mapper.parseDTO(user);
    }

    @Override
    public UserDTO edit(String id, UserUpdateDTO entity) {
        Optional<User> optional = repository.findById(id);
        if (optional.isPresent()) {
            User user = mapper.parseEntity(entity);
            user.setId(id);
            user.setAddress(optional.get().getAddress());
            user = repository.save(user);
            return mapper.parseDTO(user);
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
