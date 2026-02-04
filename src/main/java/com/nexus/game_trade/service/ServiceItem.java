package com.nexus.game_trade.service;

import com.nexus.game_trade.dto.ItemRequestDTO;
import com.nexus.game_trade.dto.ItemResponseDTO;
import com.nexus.game_trade.model.entities.EnumGame;
import com.nexus.game_trade.model.entities.EnumRarityItem;
import com.nexus.game_trade.model.entities.Item;
import com.nexus.game_trade.repository.RepositoryItem;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiceItem {

    private final RepositoryItem repository;

    public ServiceItem(RepositoryItem repository) {
        this.repository = repository;
    }

    public ItemResponseDTO saveItem(ItemRequestDTO dto) {
        Item item = new Item();

        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setGame(dto.getGame());
        item.setRarity(dto.getRarity());
        item.setPrice(dto.getPrice());
        item.setImageUrl(dto.getImageUrl());
        item.setStatus(dto.getStatus());
        item.setRegistrationDate(LocalDate.now());

        Item saved = repository.save(item);
        return toResponseDTO(saved);
    }

    public ItemResponseDTO findByJogo(EnumGame game) {
        Item item = repository.findByJogo(game);

        return toResponseDTO(item);
    }

    public ItemResponseDTO findByRarity(EnumRarityItem rarity) {
        Item item = repository.findByRarity(rarity);

        return toResponseDTO(item);
    }

    public ItemResponseDTO UpdateItem(UUID id, ItemResponseDTO dto) {
        Optional<Item> optionalItem = repository.findById(id);
        Item item = optionalItem.get();

        Item savedItem = repository.save(item);

        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setGame(dto.getGame());
        item.setRarity(dto.getRarity());
        item.setPrice(dto.getPrice());
        item.setImageUrl(dto.getImageUrl());
        item.setStatus(dto.getStatus());

        return toResponseDTO(savedItem);
    }

    public void deleteItem(UUID id) {
        Item item = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found with id: " + id));

        repository.delete(item);
    }

    private ItemResponseDTO toResponseDTO(Item item) {
        return new ItemResponseDTO(
                item.getId(), item.getName(), item.getDescription(),
                item.getGame(), item.getRarity(), item.getPrice(),
                item.getImageUrl(), item.getStatus(), item.getRegistrationDate()

        );
    }
}
