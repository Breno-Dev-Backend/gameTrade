package com.nexus.game_trade.service;

import com.nexus.game_trade.dto.DtoUser;
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

    public DtoUser.UserResponseDTO saveUser(DtoUser.UserRequestDTO dto) {
        User user = new User();
        user.setNickname(dto.nickname());
        user.setEmail(dto.email());
        user.setPassword(dto.password()); // Risco: Requer Hashing
        user.setRegistrationDate(LocalDate.now());
        user.setLevel(dto.level());

        User saved = repository.save(user);
        return toResponseDTO(saved);
    }

    public DtoUser.UserResponseDTO findByNickname(String nickname) {
        User user = repository.findByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return toResponseDTO(user);
    }

    public DtoUser.UserResponseDTO updateUser(String nickname, DtoUser.UserRequestDTO dto) {
        User user = repository.findByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        user.setEmail(dto.email());
        user.setPassword(dto.password());
        // Nickname geralmente é imutável em sistemas de trade, mas se for alterar:
        user.setNickname(dto.nickname());

        return toResponseDTO(repository.save(user));
    }

    public void deleteUser(String nickname) {
        User user = repository.findByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        repository.delete(user);
    }

    // Mapper Simples (Poderia usar MapStruct em projetos maiores)
    private DtoUser.UserResponseDTO toResponseDTO(User user) {
        return new DtoUser.UserResponseDTO(
                user.getId(), user.getNickname(), user.getEmail(),
                user.getAvgReputation(), user.getTotalExchanges(), user.getLevel()
        );
    }
}
