package com.comeon.player_service.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.comeon.player_service.api.dtos.CreatePlayerDTO;
import com.comeon.player_service.api.dtos.LoginPlayerDTO;
import com.comeon.player_service.domain.model.Session;
import com.comeon.player_service.domain.service.PlayerService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/players")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;

    @RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public void createPlayer(@Validated @RequestBody CreatePlayerDTO createPlayerDTO) {
        var domain = ToDomainTransformer.toDomain(createPlayerDTO);
        playerService.createPlayer(domain);
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public @ResponseBody Session loginPlayer(@Validated @RequestBody LoginPlayerDTO loginPlayerDTO) {
        return playerService.loginPlayer(loginPlayerDTO.getEmail(), loginPlayerDTO.getPassword());
    }

    @RequestMapping(path = "/logout/{sessionId}", method = RequestMethod.POST)
    public @ResponseBody void logoutPlayer(@PathVariable UUID sessionId) {
        playerService.logoutPlayer(sessionId);
    }
}
