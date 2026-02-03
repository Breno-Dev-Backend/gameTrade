package com.nexus.game_trade.dto;

import com.nexus.game_trade.model.entities.EnumUsers;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class UserResponseDTO {
           private UUID id;
           private String nickname;
           private String email;
           private Double avgReputation;
           private Integer totalExchanges;
           private EnumUsers level;


    public UserResponseDTO(UUID id, String nickname, String email, Double avgReputation, Integer totalExchanges, EnumUsers level) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.avgReputation = avgReputation;
        this.totalExchanges = totalExchanges;
        this.level = level;
    }
}

