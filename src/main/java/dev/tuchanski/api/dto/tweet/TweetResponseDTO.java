package dev.tuchanski.api.dto.tweet;

import java.util.Date;
import java.util.UUID;

public record TweetResponseDTO(
        UUID id,
        String content,
        Date createdAt,
        Date updatedAt,
        String username
){
}
