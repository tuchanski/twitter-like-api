package dev.tuchanski.api.dto.follow;

import java.util.UUID;

public record FollowResponseDTO(
        UUID followId,
        String followed,
        String follower
) {
}
