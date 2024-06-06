package com.comeon.player_service.domain.service;

import com.comeon.player_service.domain.model.Player;
import java.time.LocalDate;

public final class TestModels {
    public static Player.PlayerBuilder playerBuilder(String email, String password) {
        return Player.builder().name("name")
                     .surname("surname")
                     .address("address")
                     .dateOfBirth(LocalDate.EPOCH)
                     .dailyLimitInSeconds(60)
                     .password(password)
                     .email(email);
    }
}
