package com.comeon.player_service.persistence.repository;

import com.comeon.player_service.persistence.entity.PlayerEntity;
import java.time.LocalDate;

public final class TestEntities {
    public static PlayerEntity.PlayerEntityBuilder playerEntityBuilder(String email, String password) {
        return PlayerEntity.builder()
                           .email(email).name("name").surname("surname").password(password)
                           .address("address").dateOfBirth(LocalDate.EPOCH)
                           .dailyLimitInSeconds(60);
    }
}
