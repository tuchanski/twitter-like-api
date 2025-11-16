package dev.tuchanski.api.service.tweet;

import dev.tuchanski.api.dto.tweet.TweetRequestDTO;
import dev.tuchanski.api.dto.tweet.TweetResponseDTO;

import java.util.List;
import java.util.UUID;

public interface TweetService {
    TweetResponseDTO create(String token, TweetRequestDTO tweetRequestDTO);
    List<TweetResponseDTO> findAll();
    List<TweetResponseDTO> findAllByUsername(String username);
    TweetResponseDTO findById(UUID id);
    TweetResponseDTO update(String token, UUID id, TweetRequestDTO tweetRequestDTO);
    void delete(String token, UUID id);
}
