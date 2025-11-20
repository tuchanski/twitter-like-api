package dev.tuchanski.api.dto.user;

import dev.tuchanski.api.entity.enums.UserRole;

import java.util.Date;
import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String name,
        String username,
        String email,
        String bio,
        UserRole userRole
) {
}
