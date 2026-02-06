package com.nexus.game_trade.service;

import com.nexus.game_trade.dto.UserRequestDTO;
import com.nexus.game_trade.dto.UserResponseDTO;
import com.nexus.game_trade.exception.ConflictException;
import com.nexus.game_trade.exception.NotFoundException;
import com.nexus.game_trade.exception.ValidationException;
import com.nexus.game_trade.model.entities.User;
import com.nexus.game_trade.repository.RepositoryUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ServiceUsers {
    private final RepositoryUser repository;


    public UserResponseDTO saveUser(UserRequestDTO dto) {
        // Validações
        if (dto.getNickname() == null || dto.getNickname().isBlank()) {
            throw new ValidationException("Nickname é obrigatório");
        }
        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new ValidationException("Email é obrigatório");
        }
        if (!dto.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new ValidationException("Email inválido");
        }
        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new ValidationException("Senha é obrigatória");
        }
        if (dto.getPassword().length() < 6) {
            throw new ValidationException("Senha deve ter no mínimo 6 caracteres");
        }

        // Verificar duplicatas
        if (repository.findByNickname(dto.getNickname()).isPresent()) {
            throw new ConflictException("Nickname '" + dto.getNickname() + "' já está em uso");
        }
        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new ConflictException("Email '" + dto.getEmail() + "' já está cadastrado");
        }

        User user = new User();
        user.setNickname(dto.getNickname());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRegistrationDate(LocalDate.now());
        user.setLevel(dto.getLevel());

        User saved = repository.save(user);
        return toResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findByNickname(String nickname) {
        User user = repository.findByNickname(nickname)
                .orElseThrow(() -> new NotFoundException("Usuário com nickname '" + nickname + "' não encontrado"));
        return toResponseDTO(user);
    }

    public UserResponseDTO updateUser(String nickname, UserRequestDTO dto) {
        // Validações
        if (dto.getEmail() != null && !dto.getEmail().isBlank() && !dto.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new ValidationException("Email inválido");
        }
        if (dto.getPassword() != null && !dto.getPassword().isBlank() && dto.getPassword().length() < 6) {
            throw new ValidationException("Senha deve ter no mínimo 6 caracteres");
        }

        User user = repository.findByNickname(nickname)
                .orElseThrow(() -> new NotFoundException("Usuário com nickname '" + nickname + "' não encontrado"));

        // Verificar se o novo email já existe (se foi alterado)
        if (dto.getEmail() != null && !dto.getEmail().isBlank() && !dto.getEmail().equals(user.getEmail())) {
            if (repository.findByEmail(dto.getEmail()).isPresent()) {
                throw new ConflictException("Email '" + dto.getEmail() + "' já está cadastrado");
            }
            user.setEmail(dto.getEmail());
        }

        // Verificar se o novo nickname já existe (se foi alterado)
        if (dto.getNickname() != null && !dto.getNickname().isBlank() && !dto.getNickname().equals(user.getNickname())) {
            if (repository.findByNickname(dto.getNickname()).isPresent()) {
                throw new ConflictException("Nickname '" + dto.getNickname() + "' já está em uso");
            }
            user.setNickname(dto.getNickname());
        }

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(dto.getPassword());
        }

        return toResponseDTO(repository.save(user));
    }

    public void deleteUser(String nickname) {
        User user = repository.findByNickname(nickname)
                .orElseThrow(() -> new NotFoundException("Usuário com nickname '" + nickname + "' não encontrado"));
        repository.delete(user);
    }

    private UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(), user.getNickname(), user.getEmail(),
                user.getAvgReputation(), user.getTotalExchanges(), user.getLevel()
        );
    }
}
