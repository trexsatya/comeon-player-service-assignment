package com.comeon.player_service.domain.model;

import java.time.Instant;
import java.util.UUID;
import lombok.Builder;

@Builder
public class Session {
    UUID sessionId;
    Instant startedAt;
    long activeTimeInSeconds;
}
