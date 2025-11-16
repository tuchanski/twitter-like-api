package dev.tuchanski.api.dto.comment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CommentRequestDTO(
        @NotEmpty @Size(min = 1, max = 1024) String content
) {
}
