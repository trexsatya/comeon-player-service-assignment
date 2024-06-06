package com.comeon.player_service.persistence.repository;

import static com.comeon.player_service.persistence.repository.TestEntities.playerEntityBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.comeon.player_service.persistence.entity.PlayerEntity;
import com.comeon.player_service.persistence.entity.SessionEntity;
import java.time.Instant;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class SessionRepositoryTest {
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    SessionRepository sessionRepository;

    @Test
    void findByEmailAndPassword() {
        // Given
        var email = "test@email.com";
        var password = "pass";
        var playerEntity = playerEntityBuilder(email, password).build();
        playerRepository.save(playerEntity);

        SessionEntity sessionEntity = SessionEntity.builder().startedAt(Instant.now()).player(playerEntity).build();
        sessionRepository.save(sessionEntity);
        // When
        var byEmailAndPassword = sessionRepository.findByEmailAndPassword(email, password);

        // Then
        assertThat(byEmailAndPassword).isPresent();
    }
}