package com.comeon.player_service.domain.service;

import com.comeon.player_service.domain.model.Player;
import com.comeon.player_service.domain.model.Session;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerService {
    @Transactional(propagation = Propagation.REQUIRED)
    public void createPlayer(Player player) {

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Session loginPlayer(String email, String password) {
        // login player
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void logoutPlayer(UUID sessionId) {
        // logout player
    }
}
