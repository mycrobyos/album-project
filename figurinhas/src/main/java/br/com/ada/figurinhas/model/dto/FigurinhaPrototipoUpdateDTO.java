package br.com.ada.figurinhas.model.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class FigurinhaPrototipoUpdateDTO {
    @NotBlank(message = "albumPrototipoUuid field is missing or blank.")
    private String albumPrototipoId;

    @NotNull(message = "Number field is missing or blank.")
    private Integer number;

    @NotBlank(message = "Description field is missing or blank.")
    private String description;

    @NotBlank(message = "Image field is missing or blank.")
    private String image;

    @NotNull(message = "Rarity field is missing or blank.")
    //@Pattern(regexp="[1-4]")
    private Integer rarity;

    @NotNull(message = "Price field is missing or blank.")
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=10, fraction=2)
    private BigDecimal figurinhaPrice;
}
