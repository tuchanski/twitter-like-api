package dev.tuchanski.api.mapper;

import dev.tuchanski.api.dto.tweet.TweetRequestDTO;
import dev.tuchanski.api.dto.tweet.TweetResponseDTO;
import dev.tuchanski.api.entity.Tweet;
import org.springframework.stereotype.Component;

@Component
public class TweetMapper {

    public TweetResponseDTO toDTO(Tweet tweet) {
        return new TweetResponseDTO(
                tweet.getId(),
                tweet.getContent(),
                tweet.getCreatedAt(),
                tweet.getUpdatedAt(),
                tweet.getUser().getUsername()
        );
    }

    public Tweet toEntity(TweetRequestDTO tweetRequestDTO) {
        Tweet tweet = new Tweet();
        tweet.setContent(tweetRequestDTO.content());
        return tweet;
    }

}
