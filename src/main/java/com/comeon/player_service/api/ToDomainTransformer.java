package com.comeon.player_service.api;

import com.comeon.player_service.api.dtos.LoginPlayerDTO;
import com.comeon.player_service.domain.model.Player;
import com.comeon.player_service.domain.model.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ToDomainTransformer {
    public static Player toDomain(com.comeon.player_service.api.dtos.CreatePlayerDTO createPlayerDTO) {
        return Player.builder()
                     .address(createPlayerDTO.getAddress())
                     .name(createPlayerDTO.getName())
                     .surname(createPlayerDTO.getSurname())
                     .email(createPlayerDTO.getEmail())
                     .password(createPlayerDTO.getPassword())
                     .build();
    }
}
