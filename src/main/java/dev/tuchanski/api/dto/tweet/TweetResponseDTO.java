package dev.tuchanski.api.dto.tweet;

import java.util.Date;
import java.util.UUID;

public record TweetResponseDTO(
        UUID id,
        String content,
        Date createdAT,
        UUID userId
){
}
