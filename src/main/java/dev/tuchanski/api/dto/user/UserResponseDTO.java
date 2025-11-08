package dev.tuchanski.api.dto.user;

import java.util.Date;
import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String name,
        String username,
        String email,
        String bio,
        Date createdAt
) {
}
