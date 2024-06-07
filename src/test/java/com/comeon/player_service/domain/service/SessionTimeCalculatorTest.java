package com.comeon.player_service.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.comeon.player_service.persistence.entity.SessionEntity;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;


class SessionTimeCalculatorTest {

    @Test
    void test_OlderThanADay() {
        var sessionTimeCalculator = new SessionTimeCalculator();
        var builder = SessionEntity.builder().createdAt(Instant.now().minus(12, ChronoUnit.HOURS));

        assertThat(sessionTimeCalculator.olderThanADay(builder.build())).isFalse();

        builder = builder.createdAt(Instant.now().minus(25, ChronoUnit.HOURS));
        assertThat(sessionTimeCalculator.olderThanADay(builder.build())).isTrue();
    }

    @Test
    void test_TimeLimitReached() {
        var sessionTimeCalculator = new SessionTimeCalculator();
        Instant twoHoursAgo = Instant.now().minus(2, ChronoUnit.HOURS);
        var builder = SessionEntity.builder()
                                   .lastLoginAt(twoHoursAgo)
                                   .createdAt(twoHoursAgo)
                                   .lastLogoutAt(twoHoursAgo.plusSeconds(2))
                                   .activeTimeInSeconds(1);

        assertThat(sessionTimeCalculator.timeLimitReached(builder.build(), 2)).isFalse();

        //Logged in two hours ago but hasn't logged out
        assertThat(sessionTimeCalculator.timeLimitReached(builder.lastLogoutAt(null).build(), 2)).isTrue();
    }
}