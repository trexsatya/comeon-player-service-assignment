package com.comeon.player_service.persistence.repository;

import com.comeon.player_service.persistence.entity.PlayerEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {
    Optional<PlayerEntity> findByEmailAndPassword(String email, String password);
}
