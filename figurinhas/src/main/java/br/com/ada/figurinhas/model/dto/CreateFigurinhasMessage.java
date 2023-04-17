package br.com.ada.figurinhas.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateFigurinhasMessage {
    private String albumId;
    private String albumPrototipoId;
}
