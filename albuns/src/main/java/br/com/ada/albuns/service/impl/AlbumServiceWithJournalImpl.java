package br.com.ada.albuns.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.ada.albuns.model.dto.AlbumDTO;
import br.com.ada.albuns.model.entity.AlbumJournal;
import br.com.ada.albuns.model.entity.AlbumPrototipo;
import br.com.ada.albuns.repository.AlbumJournalRepository;
import br.com.ada.albuns.repository.AlbumPrototipoRepository;
import br.com.ada.albuns.service.AlbumService;
import jakarta.persistence.EntityNotFoundException;

// Using the Design Pattern: Decorator Pattern
// The AlbumServiceWithJournal is an implementation that creates the album and logs the
// transaction to the album journal. The other methods are simply forwarded to the base
// implementation with no further action.
// SOLID: the motivation here is the Single Responsibility principle of the SOLID, as
// each service is responsible for a single responsibility: one creates the album,
// the other logs the album creation to the album journal.
@Qualifier("AlbumServiceWithJournal")
@Service
public class AlbumServiceWithJournalImpl implements AlbumService {

  private final AlbumService albumService;
  private final AlbumJournalRepository repository;
  private final AlbumPrototipoRepository albumPrototipoRepository;

  public AlbumServiceWithJournalImpl(AlbumService albumService, AlbumJournalRepository repository, AlbumPrototipoRepository albumPrototipoRepository) {
    this.albumService = albumService;
    this.repository = repository;
    this.albumPrototipoRepository = albumPrototipoRepository;
  }

  @Override
  public List<AlbumDTO> findAll() {
    return albumService.findAll();
  }

  @Override
  public AlbumDTO findById(String id) {
    return albumService.findById(id);
  }

  @Override
  public AlbumDTO create(AlbumDTO entity) {
    // Executes the base implementation behavior 
    AlbumDTO newAlbum = albumService.create(entity);
    
    // And then add the behavior to log the transaction to the album journal
    AlbumPrototipo albumPrototipo = albumPrototipoRepository.findById(newAlbum.getAlbumPrototipoId()).orElseThrow(() -> new EntityNotFoundException());
    
    AlbumJournal albumJournal = AlbumJournal.builder()
    		.usuarioId(newAlbum.getUsuarioId())
    		.albumId(newAlbum.getId())
    		.albumPrototipoId(albumPrototipo.getId())
    		.albumPrototipoName(albumPrototipo.getName())
    		.price(albumPrototipo.getPrice())
    		.dateTime(LocalDateTime.now())
    		.build();
    
    repository.save(albumJournal);
    
    // Returns the result of the base implementation
    return newAlbum;
  }

  @Override
  public AlbumDTO findAlbumPadrao(String albumPrototipoId) {
    return albumService.findAlbumPadrao(albumPrototipoId);
  }
}
