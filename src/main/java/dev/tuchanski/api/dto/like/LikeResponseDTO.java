package dev.tuchanski.api.dto.like;

import java.util.UUID;

public record LikeResponseDTO(
        UUID id,
        String username,
        UUID postId
) {
}
