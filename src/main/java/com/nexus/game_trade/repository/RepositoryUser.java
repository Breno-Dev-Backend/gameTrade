package com.nexus.game_trade.repository;

import com.nexus.game_trade.model.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RepositoryUsers extends JpaRepository<Users, UUID> {

    Users createUser();
    
}
