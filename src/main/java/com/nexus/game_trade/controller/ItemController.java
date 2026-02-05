// ItemController.java
package com.nexus.game_trade.controller;

import com.nexus.game_trade.dto.ItemRequestDTO;
import com.nexus.game_trade.dto.ItemResponseDTO;
import com.nexus.game_trade.model.entities.EnumGame;
import com.nexus.game_trade.model.entities.EnumRarityItem;
import com.nexus.game_trade.service.ServiceItem;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ServiceItem serviceItem;

    @PostMapping
    public ResponseEntity<ItemResponseDTO> createItem(@Valid @RequestBody ItemRequestDTO itemRequest) {
        ItemResponseDTO createdItem = serviceItem.saveItem(itemRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }

    @GetMapping
    public ResponseEntity<List<ItemResponseDTO>> getAllItems() {
        List<ItemResponseDTO> items = serviceItem.findAll();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> getItemById(@PathVariable UUID id) {
        ItemResponseDTO item = serviceItem.findById(id);
        return ResponseEntity.ok(item);
    }

    @GetMapping("/game/{game}")
    public ResponseEntity<List<ItemResponseDTO>> getItemsByGame(@PathVariable EnumGame game) {
        List<ItemResponseDTO> items = serviceItem.findAllByGame(game);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/rarity/{rarity}")
    public ResponseEntity<List<ItemResponseDTO>> getItemsByRarity(@PathVariable EnumRarityItem rarity) {
        List<ItemResponseDTO> items = serviceItem.findByRarity(rarity);
        return ResponseEntity.ok(items);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> updateItem(
            @PathVariable UUID id,
            @Valid @RequestBody ItemRequestDTO itemRequest) {
        ItemResponseDTO updatedItem = serviceItem.updateItem(id, itemRequest);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable UUID id) {
        serviceItem.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}