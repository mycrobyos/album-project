package br.com.ada.figurinhas.service.impl;

import br.com.ada.figurinhas.model.dto.FigurinhaJournalCreationDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaJournalDTO;
import br.com.ada.figurinhas.model.entity.Figurinha;
import br.com.ada.figurinhas.model.entity.FigurinhaJournal;
import br.com.ada.figurinhas.model.mapper.FigurinhaJournalMapper;
import br.com.ada.figurinhas.model.mapper.FigurinhaMapper;
import br.com.ada.figurinhas.repository.FigurinhaJournalRepository;
import br.com.ada.figurinhas.service.FigurinhaJournalService;
import br.com.ada.figurinhas.service.FigurinhaService;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FigurinhaJournalServiceImpl implements FigurinhaJournalService {

    @Autowired
    RedisImpl redis;

    private final FigurinhaJournalRepository repository;
    private final FigurinhaJournalMapper mapper;
    private final FigurinhaService figurinhaService;
    private final FigurinhaMapper figurinhaMapper;

    public FigurinhaJournalServiceImpl(final FigurinhaJournalRepository repository, final FigurinhaJournalMapper mapper, final FigurinhaService figurinhaService, final FigurinhaMapper figurinhaMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.figurinhaService = figurinhaService;
        this.figurinhaMapper = figurinhaMapper;
    }
    
    @Override
    public List<FigurinhaJournalDTO> findAll() {
        return mapper.parseListDTO(repository.findAll());
    }

    @Override
    public FigurinhaJournalDTO findById(final String id) {
        Optional<FigurinhaJournal> optional = repository.findById(id);
        if (optional.isPresent()) {
            final FigurinhaJournal entity = optional.get();
            return mapper.parseDTO(entity);
        }
        throw new EntityNotFoundException();
    }

    @Override
    public FigurinhaJournalDTO create(final FigurinhaJournalCreationDTO creationDTO) {
        FigurinhaJournal entity = mapper.parseEntity(creationDTO);
        Figurinha figurinhaEntity = figurinhaMapper.parseEntity(
                figurinhaService.findById(creationDTO.getFigurinha().getId()));
        entity.setId(null);
        entity.setFigurinha(figurinhaEntity);
        entity = repository.save(entity);

        redis.save(creationDTO.getFigurinha().getId(), figurinhaEntity.getId());
        log.info(redis.get(creationDTO.getFigurinha().getId()));

        return mapper.parseDTO(entity);
    }

}
