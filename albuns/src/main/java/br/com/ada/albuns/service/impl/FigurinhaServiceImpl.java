package br.com.ada.albuns.service.impl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.ada.albuns.client.FigurinhaClient;
import br.com.ada.albuns.client.FigurinhaPrototipoClient;
import br.com.ada.albuns.client.dto.FigurinhaCreationDTO;
import br.com.ada.albuns.client.dto.FigurinhaDTO;
import br.com.ada.albuns.client.dto.FigurinhaPrototipoDTO;
import br.com.ada.albuns.model.entity.Album;
import br.com.ada.albuns.repository.AlbumRepository;
import br.com.ada.albuns.service.FigurinhaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FigurinhaServiceImpl implements FigurinhaService {
	private final AlbumRepository albumRepository;
	private final FigurinhaPrototipoClient figurinhaPrototipoClient;
	private final FigurinhaClient figurinhaClient;
	
	public FigurinhaServiceImpl(AlbumRepository albumRepository, FigurinhaPrototipoClient figurinhaPrototipoClient, FigurinhaClient figurinhaClient) {
		this.albumRepository = albumRepository;
		this.figurinhaPrototipoClient = figurinhaPrototipoClient;
		this.figurinhaClient = figurinhaClient;
	}

	@Override
	public void createFigurinhasForAlbum(String albumPrototipoId) {
	    ResponseEntity<List<FigurinhaPrototipoDTO>> figurinhaPrototiposResponse = figurinhaPrototipoClient.findAll(albumPrototipoId);
	    if (!figurinhaPrototiposResponse.getStatusCode().is2xxSuccessful()) {
	    	log.error("Error retrieving figurinha prototipos: {}", figurinhaPrototiposResponse.getStatusCode());
	    }

	    Album defaultAlbum = albumRepository.findByUsuarioIdAndAlbumPrototipoId(null, albumPrototipoId).orElseThrow(() -> new EntityNotFoundException());
	    List<FigurinhaPrototipoDTO> figurinhaPrototipos = figurinhaPrototiposResponse.getBody();
	    if (figurinhaPrototipos != null) {
		    figurinhaPrototipos.forEach(figurinhaPrototipo -> {
		    	this.createFigurinhas(figurinhaPrototipo, defaultAlbum);
		    });
	    }
	}
	
	private void createFigurinhas(FigurinhaPrototipoDTO figurinhaPrototipoDTO, Album album) {
    	FigurinhaCreationDTO figurinhaCreationDTO = FigurinhaCreationDTO.builder()
    			.figurinhaPrototipoId(figurinhaPrototipoDTO.getId())
    			.albumId(album.getId())
    			.build();
    	
    	int quantity = this.calculateQuantityByRarity(figurinhaPrototipoDTO);
    	
    	for (int i = 0; i < quantity; i++) {
    		
	    	log.info("Creating figurinha {} for {}", i + 1, figurinhaPrototipoDTO.getDescription());
	    	ResponseEntity<FigurinhaDTO> response = figurinhaClient.create(figurinhaCreationDTO);
	    	if (response.getStatusCode().is2xxSuccessful()) {
	    		log.info("Success: {}", response.getStatusCode());
	    	} else {
	    		log.error("Error creating figurinha: {}", response.getStatusCode());
	    	}
    	}
	}
	
	private int calculateQuantityByRarity(FigurinhaPrototipoDTO figurinhaPrototipoDTO) {
		return switch(figurinhaPrototipoDTO.getRarity()) {
			case 1 -> 1;
			case 2 -> 3;
			case 3 -> 6;
			case 4 -> 10;
			default -> 0;
		};
	}
}
