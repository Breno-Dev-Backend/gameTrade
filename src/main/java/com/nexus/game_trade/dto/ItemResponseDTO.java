package com.nexus.game_trade.dto;

import com.nexus.game_trade.model.entities.EnumGame;
import com.nexus.game_trade.model.entities.EnumRarityItem;
import com.nexus.game_trade.model.entities.EnumStatusItem;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class ItemResponseDTO {
    private UUID id;
    private String name;
    private String description;
    private EnumGame game;
    private EnumRarityItem rarity;
    private BigDecimal price;
    private String imageUrl;
    private EnumStatusItem status;
    private LocalDate registrationDate;

    public ItemResponseDTO(UUID id, String name, String description, EnumGame game, EnumRarityItem rarity, BigDecimal price, String imageUrl, EnumStatusItem status, LocalDate registrationDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.game = game;
        this.rarity = rarity;
        this.price = price;
        this.imageUrl = imageUrl;
        this.status = status;
        this.registrationDate = registrationDate;
    }
}
