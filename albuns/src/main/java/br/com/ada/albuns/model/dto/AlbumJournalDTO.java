package br.com.ada.albuns.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumJournalDTO {

  private Long id;
  private String usuarioId;
  private String albumId;
  private String albumPrototipoId;
  private String albumPrototipoName;
  private LocalDateTime dateTime;
  private BigDecimal price;
}
