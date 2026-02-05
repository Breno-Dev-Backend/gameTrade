// ItemResponseDTO.java
package com.nexus.game_trade.dto;

import com.nexus.game_trade.model.entities.EnumGame;
import com.nexus.game_trade.model.entities.EnumRarityItem;
import com.nexus.game_trade.model.entities.EnumStatusItem;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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


    private UUID sellerId;
    private String sellerName;
    private String sellerEmail;
}