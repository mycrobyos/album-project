package br.com.ada.figurinhas.service.impl;

import br.com.ada.figurinhas.exceptions.FigurinhaNotAvailableForSale;
import br.com.ada.figurinhas.model.dto.*;
import br.com.ada.figurinhas.model.entity.Figurinha;
import br.com.ada.figurinhas.model.entity.FigurinhaToSell;
import br.com.ada.figurinhas.model.mapper.FigurinhaMapper;
import br.com.ada.figurinhas.service.FigurinhaJournalService;
import br.com.ada.figurinhas.service.FigurinhaService;
import br.com.ada.figurinhas.service.FigurinhaServiceWithJournal;
import br.com.ada.figurinhas.service.FigurinhaToSellService;
import br.com.ada.figurinhas.strategy.FigurinhaPacoteStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* Design Pattern: Strategy Pattern.
 * In the buyFigurinhaPacote method, we need to get figurinhas to add to a pacote.
 * There are many strategies for choosing figurinhas. Therefore, we can create an
 * interface and several implementations of this interface that choose
 * the figurinhas in different ways. Here, we have created an algorithm that
 * chooses figurinhas randomly (RandomFigurinhaPacoteStrategyImpl.java).
 */
@Slf4j
@Service
public class FigurinhaServiceWithJournalImpl implements FigurinhaServiceWithJournal {

    private final FigurinhaJournalService figurinhaJournalService;
    private final FigurinhaService figurinhaService;
    private final FigurinhaMapper figurinhaMapper;
    private final FigurinhaToSellService figurinhaToSellService;
    private final FigurinhaPacoteStrategy figurinhaPacoteStrategy;
    public FigurinhaServiceWithJournalImpl(final FigurinhaService figurinhaService,
                                         final FigurinhaJournalService figurinhaJournalService,
                                         final FigurinhaMapper figurinhaMapper,
                                         final FigurinhaToSellService figurinhaToSellService, FigurinhaPacoteStrategy figurinhaPacoteStrategy) {
        this.figurinhaService = figurinhaService;
        this.figurinhaJournalService = figurinhaJournalService;
        this.figurinhaMapper = figurinhaMapper;
        this.figurinhaToSellService = figurinhaToSellService;
        this.figurinhaPacoteStrategy = figurinhaPacoteStrategy;
    }
    
    @Override
    public List<Figurinha> buyFigurinhaPacote(final FigurinhaBuyPacoteDTO figurinhaBuyPacoteDTO) {
        List<Figurinha> soldFigurinhas = new ArrayList<>();
        final Integer size = 5;
        final String albumId = figurinhaBuyPacoteDTO.getAlbumId();
        final String destinationAlbumId = figurinhaBuyPacoteDTO.getDestinationAlbumId();

        // Get list of figurinhas from album prototipo
        final List<Figurinha> figurinhasFromAlbumId = figurinhaService.findByAlbumId(albumId);
        final List<Figurinha> figurinhasFromDestnationAlbumId = figurinhaService.findByAlbumId(destinationAlbumId);

        // It generates a pacote of figurinhas to be sold
        soldFigurinhas = figurinhaPacoteStrategy.createFigurinhaPacote(figurinhasFromAlbumId, size);

        // Edit all figurinhas with new album id
        soldFigurinhas.forEach(figurinha -> figurinha.setAlbumId(destinationAlbumId));
        soldFigurinhas = figurinhaService.editAll(soldFigurinhas);

        // Add the sale to the transaction history (figurinha journal).
        soldFigurinhas.forEach(soldFigurinha -> this.addFigurinhaJournal(albumId, destinationAlbumId, soldFigurinha,
                soldFigurinha.getFigurinhaPrototipo().getFigurinhaPrice()));
        return soldFigurinhas;
    }

    @Override
    public Figurinha buyFigurinhaFromAlbum(final FigurinhaBuyFromAlbumDTO figurinhaBuyFromAlbumDTO) {
        Figurinha figurinha = null;
        final String figurinhaId = figurinhaBuyFromAlbumDTO.getFigurinhaId();
        final String destinationAlbumId = figurinhaBuyFromAlbumDTO.getDestinationAlbumId();

        // Get figurinha for sale
        final Optional<FigurinhaToSell> optional = figurinhaToSellService.findByFigurinhaId(figurinhaId);
        FigurinhaToSell figurinhaToSell = null;
        if (optional.isPresent()) {
            figurinhaToSell = optional.get();
            figurinha = figurinhaToSell.getFigurinha();
        } else {
            // Figurinha is not available for sale.
            String errorMsg = "Figurinha " + figurinhaId + " not available for sale";
            log.error(errorMsg);
            throw new FigurinhaNotAvailableForSale();
        }

        // Update figurinha album.
        final String sourceAlbum = figurinha.getAlbumId();
        figurinha.setAlbumId(destinationAlbumId);
        final FigurinhaUpdateDTO figurinhaUpdateDTO = FigurinhaUpdateDTO.builder()
                .albumId(figurinha.getAlbumId())
                .figurinhaPrototipoId(figurinha.getFigurinhaPrototipo().getId())
                .build();
        figurinha = figurinhaService.edit(figurinhaId, figurinhaUpdateDTO);

        // It makes the figurinha unavailable for sale.
        this.figurinhaToSellService.deleteByFigurinhaId(figurinhaId);

        // Add the sale to the transaction history (figurinha journal).
        this.addFigurinhaJournal(sourceAlbum, destinationAlbumId, figurinha, figurinhaToSell.getPrice());
        return figurinha;
    }

    private FigurinhaJournalDTO addFigurinhaJournal(final String sourceAlbum, final String destinationAlbumId,
                                                final Figurinha figurinha, final BigDecimal price) {
        final FigurinhaJournalCreationDTO figurinhaJournalCreationDTO = FigurinhaJournalCreationDTO.builder()
                .sourceAlbumId(sourceAlbum)
                .destinationAlbumId(destinationAlbumId)
                .figurinha(figurinha)
                .price(price)
                .build();
        return figurinhaJournalService.create(figurinhaJournalCreationDTO);
    }

}
