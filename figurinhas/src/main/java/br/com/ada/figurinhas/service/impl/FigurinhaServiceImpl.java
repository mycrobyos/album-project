package br.com.ada.figurinhas.service.impl;

import br.com.ada.figurinhas.model.dto.FigurinhaCreationDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaUpdateDTO;
import br.com.ada.figurinhas.model.entity.Figurinha;
import br.com.ada.figurinhas.model.entity.FigurinhaPrototipo;
import br.com.ada.figurinhas.model.mapper.FigurinhaMapper;
import br.com.ada.figurinhas.model.mapper.FigurinhaPrototipoMapper;
import br.com.ada.figurinhas.repository.FigurinhaRepository;
import br.com.ada.figurinhas.service.FigurinhaService;
import br.com.ada.figurinhas.service.FigurinhaPrototipoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FigurinhaServiceImpl implements FigurinhaService {

    private final FigurinhaRepository repository;
    private final FigurinhaMapper mapper;
    private final FigurinhaPrototipoService figurinhaPrototipoService;
    private final FigurinhaPrototipoMapper figurinhaPrototipoMapper;

    public FigurinhaServiceImpl(final FigurinhaRepository repository,
                              final FigurinhaMapper mapper,
                              final FigurinhaPrototipoService figurinhaPrototipoService,
                              final FigurinhaPrototipoMapper figurinhaPrototipoMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.figurinhaPrototipoService = figurinhaPrototipoService;
        this.figurinhaPrototipoMapper = figurinhaPrototipoMapper;
    }
    
    @Override
    public List<FigurinhaDTO> findAll() {
        return mapper.parseListDTO(repository.findAll());
    }

    @Override
    public FigurinhaDTO findById(final String id) {
        Optional<Figurinha> optional = repository.findById(id);
        if (optional.isPresent()) {
            final Figurinha entity = optional.get();
            return mapper.parseDTO(entity);
        }
        throw new EntityNotFoundException();
    }

    @Override
    //@Transactional
    public FigurinhaDTO create(final FigurinhaCreationDTO creationDTO) {
        Figurinha entity = mapper.parseEntity(creationDTO);
        FigurinhaPrototipo figurinhaPrototipoEntity = figurinhaPrototipoMapper.parseEntity(
                figurinhaPrototipoService.findById(creationDTO.getFigurinhaPrototipoId()));
        entity.setId(null);
        entity.setFigurinhaPrototipo(figurinhaPrototipoEntity);
        entity = repository.save(entity);
        //em.refresh(entity);
        return mapper.parseDTO(entity);
    }

    @Override
    public Figurinha edit(final String id, final FigurinhaUpdateDTO updateDTO) {
        final Optional<Figurinha> optional = repository.findById(id);
        if (optional.isPresent()) {
            Figurinha entity = optional.get();
            entity.setId(id);
            entity = repository.save(entity);
            return entity;
        }
        throw new EntityNotFoundException();
    }

    @Override
    public List<Figurinha> editAll(final List<Figurinha> entities) {
        for (Figurinha figurinhaToUpdate : entities) {
            if (!repository.existsById(figurinhaToUpdate.getId())) {
                throw new EntityNotFoundException();
            }
        }
        repository.saveAll(entities);
        return entities;
    }

    @Override
    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException();
        }
        repository.deleteById(id);
    }

    @Override
    public List<Figurinha> findByAlbumId(String albumId) {
        return repository.findByAlbumId(albumId);
    }

}
