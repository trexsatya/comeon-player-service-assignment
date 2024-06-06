package com.comeon.player_service.domain;

import com.comeon.player_service.domain.model.Player;
import com.comeon.player_service.persistence.entity.PlayerEntity;
import org.springframework.stereotype.Component;

public class ToJpaTransformer {
    public static PlayerEntity toJpa(Player player) {
        return PlayerEntity.builder().email(player.getEmail())
                           .password(player.getPassword())
                           .name(player.getName())
                           .surname(player.getSurname())
                           .dateOfBirth(player.getDateOfBirth())
                           .address(player.getAddress())
                           .dailyLimitInSeconds(player.getDailyLimitInSeconds())
                           .build();
    }
}
