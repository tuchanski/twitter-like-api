package dev.tuchanski.api.service.like;

import dev.tuchanski.api.dto.like.LikeResponseDTO;
import dev.tuchanski.api.entity.Tweet;
import dev.tuchanski.api.entity.User;

import java.util.List;
import java.util.UUID;

public interface LikeService {
    LikeResponseDTO create(String token, UUID tweetId);
    LikeResponseDTO findById(UUID id);
    List<LikeResponseDTO> findAllByTweet(UUID tweetId);
    List<LikeResponseDTO> findAllByUser(UUID userId);
    void deleteById(String token, UUID tweetId);
}
