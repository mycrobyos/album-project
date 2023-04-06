package br.com.ada.figurinhas.strategy;

import br.com.ada.figurinhas.model.entity.Figurinha;

import java.util.List;

public interface FigurinhaPackStrategy {
    List<Figurinha> createFigurinhaPack(List<Figurinha> figurinhas, Integer size);
}
