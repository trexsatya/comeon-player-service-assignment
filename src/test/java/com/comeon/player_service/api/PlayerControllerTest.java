package com.comeon.player_service.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.comeon.player_service.domain.service.PlayerService;
import java.util.UUID;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {PlayerController.class})
class PlayerControllerTest {

    @MockBean PlayerService playerService;

    @Autowired MockMvc mockMvc;

    @Test
    @SneakyThrows
    void createPlayer_RequiredValueMissing_ShouldFail() {
        String json = """
                    {
                      "email": "test@email.com",
                      "name" : "Test",
                      "surname": "Surname",
                      "dateOfBirth": "1990-07-07",
                      "address": "Address"
                     }
                """;
        mockMvc.perform(post("/api/v1/players")
                                .contentType(MediaType.APPLICATION_JSON).content(json))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.password").value("must not be null"));
    }

    @Test
    @SneakyThrows
    void createPlayer_OkResponse() {
        String json = """
                    {
                      "email": "test@email.com",
                      "name" : "Test",
                      "surname": "Surname",
                      "password": "pass",
                      "dateOfBirth": "1990-07-07",
                      "address": "Address"
                     }
                """;
        mockMvc.perform(post("/api/v1/players")
                                .contentType(MediaType.APPLICATION_JSON).content(json))
               .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void loginRequest_OkResponse() {
        String json = """
                    {
                      "email": "test@email.com",
                      "password": "pass"
                     }
                """;
        mockMvc.perform(post("/api/v1/players/login")
                                .contentType(MediaType.APPLICATION_JSON).content(json))
               .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void logoutRequest_OkResponse() {
        mockMvc.perform(post("/api/v1/players/logout/" + UUID.randomUUID()))
               .andExpect(status().isOk());
    }
}