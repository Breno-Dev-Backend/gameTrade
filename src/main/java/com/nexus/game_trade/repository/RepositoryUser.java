package com.nexus.game_trade.repository;

import com.nexus.game_trade.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RepositoryUser extends JpaRepository<User, UUID> {

    Optional<User> findByNickname(String nickname);

}
