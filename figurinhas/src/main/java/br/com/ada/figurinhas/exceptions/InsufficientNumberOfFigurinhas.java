package br.com.ada.figurinhas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Insufficient number of figurinhas to assemble the pacote.")
public class InsufficientNumberOfFigurinhas extends RuntimeException {
    public InsufficientNumberOfFigurinhas() {
        super("Insufficient number of figurinhas to assemble the pacote");
    }
}
