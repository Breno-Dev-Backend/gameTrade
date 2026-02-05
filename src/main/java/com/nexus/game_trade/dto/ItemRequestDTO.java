// ItemRequestDTO.java
package com.nexus.game_trade.dto;

import com.nexus.game_trade.model.entities.EnumGame;
import com.nexus.game_trade.model.entities.EnumRarityItem;
import com.nexus.game_trade.model.entities.EnumStatusItem;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter 3-100 caracteres")
    private String name;

    @Size(max = 500, message = "Descrição muito longa")
    private String description;

    @NotNull(message = "Jogo é obrigatório")
    private EnumGame game;

    @NotNull(message = "Raridade é obrigatória")
    private EnumRarityItem rarity;

    @NotNull(message = "Preço é obrigatório")
    @PositiveOrZero(message = "Preço não pode ser negativo")
    private BigDecimal price;

    @URL(message = "URL da imagem inválida")
    private String imageUrl;

    @NotNull(message = "Status é obrigatório")
    private EnumStatusItem status;

    @NotNull(message = "Seller ID é obrigatório")
    private UUID sellerId;


}