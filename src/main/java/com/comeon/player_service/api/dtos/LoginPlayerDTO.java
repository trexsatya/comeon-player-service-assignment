package com.comeon.player_service.api.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginPlayerDTO {
    private String email;
    private String password;
}
