package br.com.ada.usuarios.service.impl;

import br.com.ada.usuarios.model.dto.PhoneCreationDTO;
import br.com.ada.usuarios.model.dto.PhoneDTO;
import br.com.ada.usuarios.model.dto.PhoneUpdateDTO;
import br.com.ada.usuarios.model.entity.Phone;
import br.com.ada.usuarios.model.mapper.PhoneMapper;
import br.com.ada.usuarios.repository.PhoneRepository;
import br.com.ada.usuarios.service.PhoneService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhoneServiceImpl implements PhoneService {

    private final PhoneRepository repository;
    private final PhoneMapper mapper;
    public PhoneServiceImpl(PhoneRepository repository, PhoneMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    
    @Override
    public List<PhoneDTO> findAll() {
        return mapper.parseListDTO(repository.findAll());
    }

    @Override
    public PhoneDTO findById(String id) {
        Optional<Phone> optional = repository.findById(id);
        if (optional.isPresent()) {
            Phone entity = optional.get();
            return mapper.parseDTO(entity);
        }
        throw new EntityNotFoundException();
    }

    @Override
    public PhoneDTO create(PhoneCreationDTO creationDTO) {
        Phone entity = mapper.parseEntity(creationDTO);
        entity.setId(null);
        repository.save(entity);
        return mapper.parseDTO(entity);
    }

    @Override
    public PhoneDTO edit(String id, PhoneUpdateDTO updateDTO) {
        Optional<Phone> optional = repository.findById(id);
        if (optional.isPresent()) {
            Phone entity = mapper.parseEntity(updateDTO);
            entity.setId(id);
            entity = repository.save(entity);
            return mapper.parseDTO(entity);
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
}
