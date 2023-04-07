package br.com.ada.albuns.service.impl;

import br.com.ada.albuns.model.dto.AlbumDTO;
import br.com.ada.albuns.model.entity.Album;
import br.com.ada.albuns.model.mapper.AlbumMapper;
import br.com.ada.albuns.repository.AlbumRepository;
import br.com.ada.albuns.service.AlbumService;
import br.com.ada.albuns.service.FigurinhaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class  AlbumServiceImpl implements AlbumService {

  private final AlbumRepository repository;
  private final AlbumMapper mapper;
  private final FigurinhaService figurinhaService;

  public AlbumServiceImpl(AlbumRepository repository, AlbumMapper mapper,
		  FigurinhaService figurinhaService) {
    this.repository = repository;
    this.mapper = mapper;
    this.figurinhaService = figurinhaService;
  }

  @Override
  public List<AlbumDTO> findAll() {
    return mapper.parseListDTO(repository.findAll());
  }

  @Override
  public AlbumDTO findById(String id) {
    Album album = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    return mapper.parseDTO(album);
  }

  @Override
  public AlbumDTO create(AlbumDTO entity) {
    Album album = mapper.parseEntity(entity);
    album.setId(null);

    boolean shouldRevertAlbumCreation = false;
    album = repository.save(album);
    
    try {
	    if (!figurinhaService.createFigurinhasForAlbum(entity.getAlbumPrototipoId())) {
	    	shouldRevertAlbumCreation = true;
	    }
    } catch (Exception e) {
    	shouldRevertAlbumCreation = true;
    }
    
    if (shouldRevertAlbumCreation) {
    	repository.delete(album);
    	throw new RuntimeException("Error creating album");
    }
    
    return mapper.parseDTO(album);
  }

  @Override
  public AlbumDTO findAlbumPadrao(String albumPrototipoId) {
    Album albumPadrao = repository.findByUsuarioIdAndAlbumPrototipoId(null, albumPrototipoId).orElseThrow(() -> new EntityNotFoundException());
    return mapper.parseDTO(albumPadrao);
  }
}
