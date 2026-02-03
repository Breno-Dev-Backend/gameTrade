package com.nexus.game_trade.dto;

import com.nexus.game_trade.model.entities.EnumUsers;

import java.util.UUID;

public class DtoUser {

    public record UserRequestDTO(
            String nickname,
            String email,
            String password,
            EnumUsers level
    ) {}

    public record UserResponseDTO(
            UUID id,
            String nickname,
            String email,
            Double avgReputation,
            Integer totalExchanges,
            EnumUsers level
    ) {}
}
