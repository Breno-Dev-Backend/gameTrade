package com.nexus.game_trade.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tb_itens")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnumGame game;

    @Enumerated(EnumType.STRING)
    private EnumRarityItem rarity;

    @Column
    private BigDecimal price;
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private EnumStatusItem status;

    private User userId;
    private LocalDate registrationDate;

}

