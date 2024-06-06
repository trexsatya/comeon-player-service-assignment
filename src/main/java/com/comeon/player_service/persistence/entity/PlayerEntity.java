package com.comeon.player_service.persistence.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table(name = "players")
@AllArgsConstructor
@Getter
public class PlayerEntity {
    public static final Integer DEFAULT_SESSION_TIME_LIMIT = Integer.MAX_VALUE;

    @GeneratedValue
    @Id private Long id;

    @Nonnull
    @Column(unique = true)
    String email;

    @Nonnull
    String password;

    @Nonnull
    String name;

    @Nonnull
    String surname;

    @Nonnull
    LocalDate dateOfBirth;

    @Nonnull
    String address;

    @Nonnull
    Integer dailyLimitInSeconds = DEFAULT_SESSION_TIME_LIMIT;

    public PlayerEntity() {

    }
}