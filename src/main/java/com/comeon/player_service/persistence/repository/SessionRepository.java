package com.comeon.player_service.persistence.repository;

import com.comeon.player_service.persistence.entity.PlayerEntity;
import com.comeon.player_service.persistence.entity.SessionEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, UUID> {
    @Query("SELECT s FROM SessionEntity s WHERE s.player.email = :email AND s.player.password = :password")
    Optional<SessionEntity> findByEmailAndPassword(String email, String password);
}
