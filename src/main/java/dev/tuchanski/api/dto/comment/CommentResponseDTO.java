package dev.tuchanski.api.dto.comment;

import java.util.Date;
import java.util.UUID;

public record CommentResponseDTO(
        UUID id,
        String content,
        Date createdAt,
        UUID userId,
        String username
) {
}
