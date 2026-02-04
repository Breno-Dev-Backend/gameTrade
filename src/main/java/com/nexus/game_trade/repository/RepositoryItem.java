package com.nexus.game_trade.repository;

import com.nexus.game_trade.model.entities.EnumGame;
import com.nexus.game_trade.model.entities.EnumRarityItem;
import com.nexus.game_trade.model.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RepositoryItem extends JpaRepository<Item, UUID> {

    Item findByJogo(EnumGame game);
    Item findByRarity(EnumRarityItem rarity);
}
