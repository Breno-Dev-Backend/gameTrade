package com.nexus.game_trade.dto;

import com.nexus.game_trade.model.entities.EnumUsers;
import jakarta.validation.constraints.*;
import lombok.Value;

import java.util.UUID;

public class DtoUser {

    public record UserRequestDTO(
            @NotBlank(message = "Name is required")
            @Size(min = 3, max = 100, message = "The name must be between 3 and 100 characters long.")
            String nickname,

            @Email(message = "Email invalid")
            @NotBlank(message = "Email is required")
            String email,

            @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
                    message = "The password must be at least 8 characters long, with 1 letter and 1 number.")
            String password,

            @NotBlank(message = "level is required")
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
