// ServiceItem.java
package com.nexus.game_trade.service;

import com.nexus.game_trade.dto.ItemRequestDTO;
import com.nexus.game_trade.dto.ItemResponseDTO;
import com.nexus.game_trade.model.entities.EnumGame;
import com.nexus.game_trade.model.entities.EnumRarityItem;
import com.nexus.game_trade.model.entities.EnumStatusItem;
import com.nexus.game_trade.model.entities.Item;
import com.nexus.game_trade.model.entities.User; // Ou Users
import com.nexus.game_trade.repository.RepositoryItem;
import com.nexus.game_trade.repository.RepositoryUser; // Precisa deste repository
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ServiceItem {

    private final RepositoryItem itemRepository;
    private final RepositoryUser userRepository; // Adicione esta dependência

    // CREATE
    public ItemResponseDTO saveItem(ItemRequestDTO dto) {
        User seller = userRepository.findById(dto.getSellerId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found with id: " + dto.getSellerId()
                ));


        Item item = Item.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .game(dto.getGame())
                .rarity(dto.getRarity())
                .price(dto.getPrice())
                .imageUrl(dto.getImageUrl())
                .seller(seller)
                .status(dto.getStatus())
                .registrationDate(LocalDate.now())
                .build();


        Item savedItem = itemRepository.save(item);
        return toResponseDTO(savedItem);
    }


    @Transactional(readOnly = true)
    public List<ItemResponseDTO> findAll() {
        return itemRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public ItemResponseDTO findById(UUID id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found with id: " + id));
        return toResponseDTO(item);
    }


    @Transactional(readOnly = true)
    public List<ItemResponseDTO> findAllByGame(EnumGame game) {
        return itemRepository.findAllByGame(game).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<ItemResponseDTO> findByRarity(EnumRarityItem rarity) {
        return itemRepository.findAllByRarity(rarity).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }


    public ItemResponseDTO updateItem(UUID id, ItemRequestDTO dto) {

        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found with id: " + id));


        if (dto.getSellerId() != null && !dto.getSellerId().equals(item.getSeller().getId())) {
            User newSeller = userRepository.findById(dto.getSellerId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "User not found with id: " + dto.getSellerId()
                    ));
            item.setSeller(newSeller);
        }


        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setGame(dto.getGame());
        item.setRarity(dto.getRarity());
        item.setPrice(dto.getPrice());
        item.setImageUrl(dto.getImageUrl());
        item.setStatus(dto.getStatus());


        Item updatedItem = itemRepository.save(item);
        return toResponseDTO(updatedItem);
    }


    public void deleteItem(UUID id) {
        if (!itemRepository.existsById(id)) {
            throw new EntityNotFoundException("Item not found with id: " + id);
        }
        itemRepository.deleteById(id);
    }


    private ItemResponseDTO toResponseDTO(Item item) {
        return ItemResponseDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .game(item.getGame())
                .rarity(item.getRarity())
                .price(item.getPrice())
                .imageUrl(item.getImageUrl())
                .status(item.getStatus())
                .registrationDate(item.getRegistrationDate())
                .sellerId(item.getSeller().getId())
                .sellerName(item.getSeller().getNickname())
                .sellerEmail(item.getSeller().getEmail())
                .build();
    }
}