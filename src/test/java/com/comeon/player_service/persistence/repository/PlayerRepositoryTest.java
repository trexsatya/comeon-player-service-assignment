package com.comeon.player_service.persistence.repository;

import static com.comeon.player_service.persistence.repository.TestEntities.playerEntityBuilder;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PlayerRepositoryTest {
    @Autowired PlayerRepository playerRepository;

    @Test
    void findByEmailAndPassword() {
        // Given
        var email = "test@email.com";
        var password = "pass";
        var playerEntity = playerEntityBuilder(email, password).build();
        playerRepository.save(playerEntity);
        // When
        var byEmailAndPassword = playerRepository.findByEmailAndPassword(email, password);
        // Then
        assertThat(byEmailAndPassword).isPresent();
    }
}