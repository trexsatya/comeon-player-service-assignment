package com.comeon.player_service.persistence.entity;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "sessions")
public class SessionEntity {
    @GeneratedValue(strategy = GenerationType.UUID) @Id UUID id;

    @Nonnull
    @Column(name = "created_at")
    Instant createdAt;

    @Column(name = "last_login_at")
    Instant lastLoginAt;

    @Nullable
    @Column(name = "last_logout_at")
    Instant lastLogoutAt;

    @Column(name = "active_time_secs")
    Integer activeTimeInSeconds = 0;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id", foreignKey = @ForeignKey(name = "FK_player_id"))
    PlayerEntity player;

    public SessionEntity() {
    }
}
