package br.com.ada.figurinhas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Figurinha not available for sale.")
public class FigurinhaNotAvailableForSale extends RuntimeException {
    public FigurinhaNotAvailableForSale() {
        super("Figurinha not available for sale");
    }
}
