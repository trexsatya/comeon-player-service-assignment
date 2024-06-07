package com.comeon.player_service.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssumptions.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;

import com.comeon.player_service.domain.error.BadRequestException;
import com.comeon.player_service.domain.error.NotFoundException;
import com.comeon.player_service.domain.model.Session;
import com.comeon.player_service.persistence.entity.SessionEntity;
import com.comeon.player_service.persistence.repository.PlayerRepository;
import com.comeon.player_service.persistence.repository.SessionRepository;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
class PlayerServiceTest {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @SpyBean
    SessionTimeCalculator sessionTimeCalculator;

    @BeforeEach
    void init() {
        sessionRepository.deleteAll();
        playerRepository.deleteAll();
    }

    @Test
    void playerDoesNotExist_LoginAttempt_ShouldThrow() {
        assertThatThrownBy(() -> playerService.loginPlayer("email", "password"))
                .isExactlyInstanceOf(NotFoundException.class);
    }

    @Test
    void noSessionExists_LoginAttempt_ShouldCreateNewSession() {
        givenThatPlayerExists("email", "password");

        var session = playerService.loginPlayer("email", "password");
        assertThat(session).isNotNull();
        assertThat(session.getLastLoginAt()).isNotNull();
        assertThat(session.getSessionId()).isNotNull();

        assertThat(sessionRepository.findAll()).hasSize(1);
    }

    @Test
    void sessionExists_ButOlderThanADay_LoginAttempt_ShouldCreateNewSession() {
        givenThatPlayerExists("email", "password");

        var session = playerService.loginPlayer("email", "password");
        var session1 = sessionRepository.findByEmailAndPassword("email", "password").orElseThrow();
        willReturn(true).given(sessionTimeCalculator).olderThanADay(any());

        var session2 = playerService.loginPlayer("email", "password");
        assertThat(session.getSessionId()).isNotEqualTo(session2.getSessionId());
        assertThat(session2.getLastLoginAt()).isAfter(session.getLastLoginAt());
    }

    @Test
    void sessionExists_TimeLimitReached_ShouldThrow() {
        givenThatPlayerExists("email", "password");

        var session = playerService.loginPlayer("email", "password");
        var session1 = sessionRepository.findByEmailAndPassword("email", "password").orElseThrow();
        willReturn(false).given(sessionTimeCalculator).olderThanADay(any());
        willReturn(true).given(sessionTimeCalculator).timeLimitReached(any(), any());

        assertThatThrownBy(() -> playerService.loginPlayer("email", "password"))
                .isExactlyInstanceOf(BadRequestException.class);
    }

    @Test
    void sessionDoesNotExist_logoutAttempt_ShouldThrow() {
        givenThatPlayerExists("email@email.com", "passw");
        assertThatThrownBy(() -> playerService.logoutPlayer(UUID.randomUUID()))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @SneakyThrows
    void logoutAttempt_ShouldUpdateActiveTime() {
        givenThatPlayerExists("email@email.com", "passw");
        var session = playerService.loginPlayer("email@email.com", "passw");

        var prevActiveTime = session.getActiveTimeInSeconds();

        Thread.sleep(2000);
        playerService.logoutPlayer(session.getSessionId());

        var updatedActiveTimeInSeconds = sessionRepository.findById(session.getSessionId()).orElseThrow().getActiveTimeInSeconds();
        assertThat(updatedActiveTimeInSeconds).isGreaterThan(prevActiveTime);
    }

    private void givenThatPlayerExists(String email, String password) {
        playerService.createPlayer(TestModels.playerBuilder(email, password).build());
    }
}