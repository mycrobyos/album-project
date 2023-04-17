package br.com.ada.albuns.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.ada.albuns.model.dto.AlbumDTO;
import br.com.ada.albuns.model.entity.Album;
import br.com.ada.albuns.model.mapper.AlbumMapper;
import br.com.ada.albuns.model.mapper.AlbumMapperImpl;
import br.com.ada.albuns.repository.AlbumRepository;
import br.com.ada.albuns.service.FigurinhaService;
import jakarta.persistence.EntityNotFoundException;

public class AlbumServiceImplTest {
	private AlbumRepository repository;
	private AlbumMapper mapper;
	private FigurinhaService figurinhaService;
	private AlbumServiceImpl service;

	@BeforeEach
	public void setUp() {
		this.repository = mock(AlbumRepository.class);
		this.mapper = new AlbumMapperImpl();
		this.figurinhaService = mock(FigurinhaService.class);
		// this.service = new AlbumServiceImpl(repository, mapper, figurinhaService);
	}
	
	@Test
	public void testFindAll() {
		// Arrange
		String id = UUID.randomUUID().toString();
		String albumPrototipoId = "AlbumPrototipoId";
		String usuarioId = "UsuarioId";
		Album album = Album.builder()
				.id(id)
				.albumPrototipoId(albumPrototipoId)
				.usuarioId(usuarioId)
				.build();
		when(repository.findAll()).thenReturn(List.of(album));
		
		// Act
		List<AlbumDTO> actualAlbums = service.findAll();
		
		// Assert
		assertEquals(1, actualAlbums.size());
		assertEquals(id, actualAlbums.get(0).getId());
		assertEquals(albumPrototipoId, actualAlbums.get(0).getAlbumPrototipoId());
		assertEquals(usuarioId, actualAlbums.get(0).getUsuarioId());
	}

	@Test
	public void testFindById() {
		// Arrange
		String id = UUID.randomUUID().toString();
		String albumPrototipoId = "AlbumPrototipoId";
		String usuarioId = "UsuarioId";
		Album album = Album.builder()
				.id(id)
				.albumPrototipoId(albumPrototipoId)
				.usuarioId(usuarioId)
				.build();
		when(repository.findById(id)).thenReturn(Optional.of(album));
		
		// Act
		AlbumDTO actualAlbum = service.findById(id);
		
		// Assert
		assertEquals(id, actualAlbum.getId());
		assertEquals(albumPrototipoId, actualAlbum.getAlbumPrototipoId());
		assertEquals(usuarioId, actualAlbum.getUsuarioId());
	}

	@Test
	public void testFindByIdNotFound() {
		// Arrange
		String id = UUID.randomUUID().toString();
		when(repository.findById(id)).thenReturn(Optional.empty());
		
		// Act/Assert
		assertThrows(EntityNotFoundException.class, () -> service.findById(id));
	}

	@Test
	public void testCreate() {
		// Arrange
		String id = UUID.randomUUID().toString();
		String albumPrototipoId = "AlbumPrototipoId";
		String usuarioId = "UsuarioId";
		AlbumDTO albumDTO = AlbumDTO.builder()
				.albumPrototipoId(albumPrototipoId)
				.usuarioId(usuarioId)
				.build();
		Album savedAlbum = Album.builder()
				.id(id)
				.albumPrototipoId(albumPrototipoId)
				.usuarioId(usuarioId)
				.build();

		when(repository.save(any())).thenReturn(savedAlbum);
		
		// Act
		AlbumDTO actualAlbumDTO = service.create(albumDTO);
		
		// Assert
		assertEquals(id, actualAlbumDTO.getId());
		assertEquals(albumPrototipoId, actualAlbumDTO.getAlbumPrototipoId());
		assertEquals(usuarioId, actualAlbumDTO.getUsuarioId());
	}
	
	@Test
	public void testFindAlbumPadrao() {
		// Arrange
		String id = UUID.randomUUID().toString();
		String albumPrototipoId = "AlbumPrototipoId";
		Album album = Album.builder()
				.id(id)
				.albumPrototipoId(albumPrototipoId)
				.usuarioId(null)
				.build();
		
		when(repository.findByUsuarioIdAndAlbumPrototipoId(null, albumPrototipoId)).thenReturn(Optional.of(album));
		
		// Act
		AlbumDTO actualAlbumDTO = service.findAlbumPadrao(albumPrototipoId);
		
		// Assert
		assertEquals(id, actualAlbumDTO.getId());
		assertEquals(albumPrototipoId, actualAlbumDTO.getAlbumPrototipoId());
		assertNull(actualAlbumDTO.getUsuarioId());
	}

	@Test
	public void testFindAlbumPadraoNotFound() {
		// Arrange
		String albumPrototipoId = "AlbumPrototipoId";
		when(repository.findByUsuarioIdAndAlbumPrototipoId(null, albumPrototipoId)).thenReturn(Optional.empty());
		
		// Act/Assert
		assertThrows(EntityNotFoundException.class, () -> service.findAlbumPadrao(albumPrototipoId));
	}
}
