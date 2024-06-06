package com.comeon.player_service.domain.model;

import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString(onlyExplicitlyIncluded = true)
public class Session {
    @ToString.Include
    UUID sessionId;
    @ToString.Include
    Instant startedAt;

    Instant endedAt;
    Integer activeTimeInSeconds;
}
