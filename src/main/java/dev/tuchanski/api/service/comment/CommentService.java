package dev.tuchanski.api.service.comment;

import dev.tuchanski.api.dto.comment.CommentRequestDTO;
import dev.tuchanski.api.dto.comment.CommentResponseDTO;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    CommentResponseDTO create(String token, UUID tweetId, CommentRequestDTO commentRequestDTO);
    CommentResponseDTO findById(UUID id);
    List<CommentResponseDTO> findByTweetIdOrderByCreatedAtDesc(UUID tweetId);
    CommentResponseDTO update(String token, UUID id, CommentRequestDTO commentRequestDTO);
    void delete(String token, UUID id);
}
