package com.nexus.game_trade.controller;

import com.nexus.game_trade.dto.UserRequestDTO;
import com.nexus.game_trade.dto.UserResponseDTO;
import com.nexus.game_trade.model.entities.User;
import com.nexus.game_trade.service.ServiceUsers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

        private final ServiceUsers serviceUsers;

        public UserController(ServiceUsers serviceUsers) {
            this.serviceUsers = serviceUsers;
        }

        @PostMapping
        public ResponseEntity<UserResponseDTO> saveUser(@RequestBody UserRequestDTO dto) {
            return ResponseEntity.status(HttpStatus.CREATED).body(serviceUsers.saveUser(dto));
        }

        @GetMapping("/{nickname}")
        public ResponseEntity<UserResponseDTO> findByNickname(@PathVariable String nickname) {
            return ResponseEntity.ok(serviceUsers.findByNickname(nickname));
        }

        @PutMapping("/{nickname}")
        public ResponseEntity<UserResponseDTO> updateUser(
                @PathVariable String nickname,
                @RequestBody UserRequestDTO dto) {
            return ResponseEntity.ok(serviceUsers.updateUser(nickname, dto));
        }

        @DeleteMapping("/{nickname}")
        public ResponseEntity<Void> deleteUser(@PathVariable String nickname) {
            serviceUsers.deleteUser(nickname);
            return ResponseEntity.noContent().build();
        }
    }

