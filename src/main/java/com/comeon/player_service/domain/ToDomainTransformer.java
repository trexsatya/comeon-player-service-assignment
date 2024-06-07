package com.comeon.player_service.domain;

import com.comeon.player_service.domain.model.Player;
import com.comeon.player_service.domain.model.Session;
import com.comeon.player_service.persistence.entity.PlayerEntity;
import com.comeon.player_service.persistence.entity.SessionEntity;

public class ToDomainTransformer {
    public static Player toDomain(PlayerEntity playerEntity) {
        return Player.builder().email(playerEntity.getEmail())
                     .password(playerEntity.getPassword())
                     .name(playerEntity.getName())
                     .surname(playerEntity.getSurname())
                     .dateOfBirth(playerEntity.getDateOfBirth())
                     .address(playerEntity.getAddress())
                     .dailyLimitInSeconds(playerEntity.getDailyLimitInSeconds())
                     .build();
    }

    public static Session toDomain(SessionEntity sessionEntity) {
        return Session.builder()
                      .sessionId(sessionEntity.getId())
                      .activeTimeInSeconds(sessionEntity.getActiveTimeInSeconds())
                      .lastLogoutAt(sessionEntity.getLastLogoutAt())
                      .lastLoginAt(sessionEntity.getLastLoginAt())
                      .build();
    }
}
