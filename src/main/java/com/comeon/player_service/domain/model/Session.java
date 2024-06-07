package com.comeon.player_service.domain.model;

import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class Session {
    UUID sessionId;
    Instant createdAt;
    Instant lastLoginAt;
    Instant lastLogoutAt;
    Integer activeTimeInSeconds;
}
