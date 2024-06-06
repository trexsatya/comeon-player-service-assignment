package com.comeon.player_service.domain.model;

import static java.util.Optional.ofNullable;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class Player {
    String email;
    String password;
    String name;
    String surname;
    LocalDate dateOfBirth;
    String address;
    Integer dailyLimitInSeconds;
    Session session;

    public boolean isSessionExpired(Session session) {
        return session.activeTimeInSeconds >= this.dailyLimitInSeconds;
    }

    public Optional<Session> getSession() {
        return ofNullable(session);
    }
}
