package com.nexus.game_trade.service;

import com.nexus.game_trade.dto.UserRequestDTO;
import com.nexus.game_trade.dto.UserResponseDTO;
import com.nexus.game_trade.model.entities.User;
import com.nexus.game_trade.repository.RepositoryUser;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ServiceUsers {
    private final RepositoryUser repository;

    public ServiceUsers(RepositoryUser repository) {
        this.repository = repository;
    }

    public UserResponseDTO saveUser(UserRequestDTO dto) {
        User user = new User();
        user.setNickname(dto.getNickname());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword()); // Risco: Requer Hashing
        user.setRegistrationDate(LocalDate.now());
        user.setLevel(dto.getLevel());

        User saved = repository.save(user);
        return toResponseDTO(saved);
    }

    public UserResponseDTO findByNickname(String nickname) {
        User user = repository.findByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return toResponseDTO(user);
    }

    public UserResponseDTO updateUser(String nickname, UserRequestDTO dto) {
        User user = repository.findByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setNickname(dto.getNickname());

        return toResponseDTO(repository.save(user));
    }

    public void deleteUser(String nickname) {
        User user = repository.findByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        repository.delete(user);
    }

    private UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(), user.getNickname(), user.getEmail(),
                user.getAvgReputation(), user.getTotalExchanges(), user.getLevel()
        );
    }
}
