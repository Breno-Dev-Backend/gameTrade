// RepositoryItem.java
package com.nexus.game_trade.repository;

import com.nexus.game_trade.model.entities.EnumGame;
import com.nexus.game_trade.model.entities.EnumRarityItem;
import com.nexus.game_trade.model.entities.EnumStatusItem;
import com.nexus.game_trade.model.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RepositoryItem extends JpaRepository<Item, UUID> {


    List<Item> findAllByGame(EnumGame game);

    List<Item> findAllByRarity(EnumRarityItem rarity);

    List<Item> findByStatus(EnumStatusItem status);

    List<Item> findByGameAndRarity(EnumGame game, EnumRarityItem rarity);

    List<Item> findBySellerId(UUID sellerId);
}