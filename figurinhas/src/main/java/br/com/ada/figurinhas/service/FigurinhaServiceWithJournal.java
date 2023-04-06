package br.com.ada.figurinhas.service;

import br.com.ada.figurinhas.model.dto.FigurinhaBuyFromAlbumDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaBuyPackDTO;
import br.com.ada.figurinhas.model.entity.Figurinha;

import java.util.List;

public interface FigurinhaServiceWithJournal {
    List<Figurinha> buyFigurinhaPack(FigurinhaBuyPackDTO figurinhaBuyPackDTO);
    Figurinha buyFigurinhaFromAlbum(FigurinhaBuyFromAlbumDTO figurinhaBuyFromAlbumDTO);
}
