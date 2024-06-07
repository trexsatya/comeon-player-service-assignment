package com.comeon.player_service.domain.service;

import static com.comeon.player_service.domain.ToDomainTransformer.toDomain;
import static com.comeon.player_service.domain.ToJpaTransformer.toJpa;
import static java.util.function.Predicate.not;

import com.comeon.player_service.domain.error.BadRequestException;
import com.comeon.player_service.domain.error.NotFoundException;
import com.comeon.player_service.domain.model.Player;
import com.comeon.player_service.domain.model.Session;
import com.comeon.player_service.persistence.entity.PlayerEntity;
import com.comeon.player_service.persistence.entity.SessionEntity;
import com.comeon.player_service.persistence.repository.PlayerRepository;
import com.comeon.player_service.persistence.repository.SessionRepository;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * NOTE: Assumption is that a player can have only one active session in 24 hours.
 *       Player can log in multiple times in a day and the session active time will keep adding up.
 */
@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final SessionRepository sessionRepository;
    private final SessionTimeCalculator sessionTimeCalculator;

    @Transactional(propagation = Propagation.REQUIRED)
    public void createPlayer(Player player) {
        var playerEntity = toJpa(player);
        playerRepository.save(playerEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Session loginPlayer(String email, String password) {
        var playerEntity = ensureThatPlayerIsRegistered(email, password);

        var session = existingSession(email, password)
                .filter(not(sessionTimeCalculator::olderThanADay))
                .map(it -> throwIfDailyLimitReached(it, playerEntity.getDailyLimitInSeconds()))
                .orElseGet(() -> newSession(playerEntity));

        session.setLastLogoutAt(null);
        return toDomain(sessionRepository.save(session));
    }

    private SessionEntity throwIfDailyLimitReached(SessionEntity session, Integer dailyLimitInSeconds) {
        if (sessionTimeCalculator.timeLimitReached(session, dailyLimitInSeconds)) {
            throw new BadRequestException("Daily limit reached");
        }
        return session;
    }

    private Optional<SessionEntity> existingSession(String email, String password) {
        return sessionRepository.findByEmailAndPassword(email, password);
    }

    private SessionEntity newSession(PlayerEntity playerEntity) {
        return SessionEntity.builder().player(playerEntity).createdAt(Instant.now()).activeTimeInSeconds(0).lastLoginAt(Instant.now()).build();
    }

    private PlayerEntity ensureThatPlayerIsRegistered(String email, String password) {
        return playerRepository.findByEmailAndPassword(email, password)
                               .orElseThrow(() -> new NotFoundException("Player not found"));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void logoutPlayer(UUID sessionId) {
        var session = sessionRepository.findById(sessionId).orElseThrow(() -> new NotFoundException("Session not found"));
        session.setLastLogoutAt(Instant.now());
        var activeTime = session.getActiveTimeInSeconds() + Duration.between(session.getLastLoginAt(), session.getLastLogoutAt()).get(ChronoUnit.SECONDS);
        session.setActiveTimeInSeconds((int) activeTime);
    }
}
