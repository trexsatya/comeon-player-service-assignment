package com.comeon.player_service.api.dtos;

import jakarta.annotation.Nonnull;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Builder
@Data
public class CreatePlayerDTO {
    @Nonnull
    String email;
    @Nonnull
    String password;
    @Nonnull
    String name;
    @Nonnull
    String surname;
    @Nonnull
    LocalDate dateOfBirth;
    @Nonnull
    String address;
}
