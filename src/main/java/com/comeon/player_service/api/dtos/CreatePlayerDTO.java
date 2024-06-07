package com.comeon.player_service.api.dtos;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreatePlayerDTO {
    @NotNull
    String email;
    @NotNull
    String password;
    @NotNull
    String name;
    @NotNull
    String surname;
    @NotNull
    LocalDate dateOfBirth;
    @NotNull
    String address;
}
