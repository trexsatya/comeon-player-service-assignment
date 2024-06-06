package com.comeon.player_service.domain.service;

import com.comeon.player_service.persistence.entity.SessionEntity;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Component;

@Component
public class SessionTimeCalculator {

    public boolean olderThanADay(SessionEntity session) {
        return Duration.between(session.getStartedAt(), Instant.now()).get(ChronoUnit.SECONDS) >= 24*60*60;
    }

    public boolean timeLimitReached(SessionEntity session, Integer dailyLimitInSeconds) {
        return session.getActiveTimeInSeconds() != null && session.getActiveTimeInSeconds() >= dailyLimitInSeconds;
    }
}
