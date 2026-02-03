package com.nexus.game_trade.dto;

import com.nexus.game_trade.model.entities.EnumUsers;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class UserRequestDTO {

        @NotBlank(message = "Name is required")
        @Size(min = 3, max = 100, message = "The name must be between 3 and 100 characters long.")
        private String nickname;

        @Email(message = "Email invalid")
        @NotBlank(message = "Email is required")
        private String email;

        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
                message = "The password must be at least 8 characters long, with 1 letter and 1 number.")
        private String password;

        @NotBlank(message = "level is required")
        private EnumUsers level;


    }


