package dev.tuchanski.api.dto.tweet;

import dev.tuchanski.api.dto.comment.CommentResponseDTO;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record TweetResponseDTO(
        UUID id,
        String content,
        Date createdAt,
        Date updatedAt,
        String username,
        List<CommentResponseDTO> comments
){
}
