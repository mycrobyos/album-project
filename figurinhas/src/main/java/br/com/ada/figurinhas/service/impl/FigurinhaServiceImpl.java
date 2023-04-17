package br.com.ada.figurinhas.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.ada.figurinhas.model.dto.CreateFigurinhasMessage;
import br.com.ada.figurinhas.model.dto.FigurinhaCreationDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaPrototipoDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaUpdateDTO;
import br.com.ada.figurinhas.model.entity.Figurinha;
import br.com.ada.figurinhas.model.entity.FigurinhaPrototipo;
import br.com.ada.figurinhas.model.mapper.FigurinhaMapper;
import br.com.ada.figurinhas.model.mapper.FigurinhaPrototipoMapper;
import br.com.ada.figurinhas.repository.FigurinhaRepository;
import br.com.ada.figurinhas.service.FigurinhaPrototipoService;
import br.com.ada.figurinhas.service.FigurinhaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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

    @Override
    public boolean createFigurinhasForAlbum(CreateFigurinhasMessage createFigurinhaMessage) {
        List<FigurinhaDTO> figurinhasCreated = new ArrayList<>();
        boolean shouldRevertFigurinhasCreation = false;

        try {
            List<FigurinhaPrototipoDTO> figurinhaPrototipos = figurinhaPrototipoService.findAll(Optional.ofNullable(createFigurinhaMessage.getAlbumPrototipoId()));
//            if (!figurinhaPrototiposResponse.getStatusCode().equals(HttpStatus.OK)) {
//                log.error("Error retrieving figurinha templates: {}", figurinhaPrototiposResponse.getStatusCode());
//                throw new EntityNotFoundException("No figurinha template found for this album template");
//            }

            //Album defaultAlbum = albumRepository.findByUserIdAndAlbumPrototipoId(null, albumPrototipoId).orElseThrow(() -> new EntityNotFoundException("Default album not found"));

            if (figurinhaPrototipos != null) {
                for (FigurinhaPrototipoDTO figurinhaPrototipo : figurinhaPrototipos) {
                    List<FigurinhaDTO> figurinhasCreatedInThisStep =this.createFigurinhas(figurinhaPrototipo, createFigurinhaMessage);
                    if (figurinhasCreatedInThisStep != null) {
                        figurinhasCreated.addAll(figurinhasCreatedInThisStep);
                    } else {
                        shouldRevertFigurinhasCreation = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            shouldRevertFigurinhasCreation = true;
        }

        if (shouldRevertFigurinhasCreation) {
            //this.revertFigurinhasCreation(figurinhasCreated);
            return false;
        }

        return true;
    }


    private List<FigurinhaDTO> createFigurinhas(FigurinhaPrototipoDTO figurinhaPrototipoDTO, CreateFigurinhasMessage createFigurinhaMessage) {
        List<FigurinhaDTO> figurinhasCreated = new ArrayList<>();
        boolean shouldRevertFigurinhasCreation = false;

        try {
            FigurinhaCreationDTO figurinhaCreationDTO = FigurinhaCreationDTO.builder()
                    .figurinhaPrototipoId(figurinhaPrototipoDTO.getId())
                    .albumId(createFigurinhaMessage.getAlbumId())
                    .build();

            int quantity = this.calculateQuantityByRarity(figurinhaPrototipoDTO);

            for (int i = 0; i < quantity; i++) {

                log.info("Creating figurinha {} for {}", i + 1, figurinhaPrototipoDTO.getDescription());
                FigurinhaDTO response = this.create(figurinhaCreationDTO);

            }
        } catch (Exception e) {
            shouldRevertFigurinhasCreation = true;
        }

        if (shouldRevertFigurinhasCreation) {
            this.revertFigurinhasCreation(figurinhasCreated);
            return null;
        }

        return figurinhasCreated;
    }

    private int calculateQuantityByRarity(FigurinhaPrototipoDTO figurinhaPrototipoDTO) {
        return switch(figurinhaPrototipoDTO.getRaridade()) {
            case 1 -> 1;
            case 2 -> 3;
            case 3 -> 6;
            case 4 -> throw new RuntimeException("Fake exception");//10;
            default -> 0;
        };
    }

    private void revertFigurinhasCreation(List<FigurinhaDTO> figurinhasToRevert) {
        figurinhasToRevert.forEach(figurinhaToRevert -> {
            try {
                log.info("Reverting figurinha {}", figurinhaToRevert.getId());
               // figurinhaClient.delete(figurinhaToRevert.getId());
            } catch(Exception e) {
                log.error("Error reverting figurinha creation for figurinha {}: {}", figurinhaToRevert.getId(), e.getMessage());
            }
        });
    }

}
