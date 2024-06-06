package com.comeon.player_service.api;

import com.comeon.player_service.api.dtos.CreatePlayerDTO;
import com.comeon.player_service.api.dtos.LoginPlayerDTO;
import com.comeon.player_service.domain.model.Player;
import com.comeon.player_service.domain.service.PlayerService;
import com.comeon.player_service.domain.model.Session;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/players")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;
    private final ToDomainTransformer toDomainTransformer;

    @PostMapping
    public ResponseEntity<Void> createPlayer(@RequestBody CreatePlayerDTO createPlayerDTO) {
        var domain = toDomainTransformer.toDomain(createPlayerDTO);
        playerService.createPlayer(domain);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Session> loginPlayer(@RequestBody LoginPlayerDTO loginPlayerDTO) {
        var session = playerService.loginPlayer(loginPlayerDTO.getEmail(), loginPlayerDTO.getPassword());
        return ResponseEntity.ok(session);
    }

    @PostMapping("/logout/session/{sessionId}")
    public ResponseEntity<Void> logoutPlayer(@PathVariable UUID sessionId){
        playerService.logoutPlayer(sessionId);
        return ResponseEntity.ok().build();
    }
}
