package dev.tuchanski.api.dto.tweet;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record TweetRequestDTO(
        @NotEmpty(message = "Content is mandatory") @Size(min = 1, max = 1024) String content
) {
}
