package dev.tuchanski.api.dto.auth;

import java.util.UUID;

public record LoginResponseDTO(
        String token,
        UUID id,
        String username
) {
}
