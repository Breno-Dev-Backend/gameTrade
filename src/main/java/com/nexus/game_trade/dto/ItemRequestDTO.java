package com.nexus.game_trade.dto;

import com.nexus.game_trade.model.entities.EnumGame;
import com.nexus.game_trade.model.entities.EnumRarityItem;
import com.nexus.game_trade.model.entities.EnumStatusItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;


@Getter
@Builder
@ToString
@EqualsAndHashCode
public class ItemRequestDTO {
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter 3-100 caracteres")
    private String name;

    @Size(max = 500, message = "Descrição muito longa")
    private String description;

    @NotNull(message = "Jogo é obrigatório")
    private EnumGame game;

    private EnumRarityItem rarity;

    @PositiveOrZero(message = "Preço não pode ser negativo")
    private BigDecimal price;

    @URL(message = "URL da imagem inválida")
    private String imageUrl;

    @NotNull(message = "Status é obrigatório")
    private EnumStatusItem status;

    // NOTA: userId NÃO vai aqui! Vem do token/autenticação
}
