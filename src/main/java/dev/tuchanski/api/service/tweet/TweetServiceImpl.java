package dev.tuchanski.api.service.tweet;

import dev.tuchanski.api.dto.tweet.TweetRequestDTO;
import dev.tuchanski.api.dto.tweet.TweetResponseDTO;
import dev.tuchanski.api.entity.Tweet;
import dev.tuchanski.api.entity.User;
import dev.tuchanski.api.exception.tweet.ContentIsTheSameException;
import dev.tuchanski.api.exception.tweet.TweetNotBelongToUserException;
import dev.tuchanski.api.exception.tweet.TweetNotFoundException;
import dev.tuchanski.api.exception.user.UserNotFoundException;
import dev.tuchanski.api.mapper.TweetMapper;
import dev.tuchanski.api.repository.TweetRepository;
import dev.tuchanski.api.repository.UserRepository;
import dev.tuchanski.api.service.auth.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static dev.tuchanski.api.service.user.UserServiceImpl.getUser;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final TweetMapper tweetMapper;

    @Override
    public TweetResponseDTO create(String token, TweetRequestDTO tweetRequestDTO) {
        User user = getUserFromToken(token);
        Tweet tweet = tweetMapper.toEntity(tweetRequestDTO);
        tweet.setUser(user);
        tweet = tweetRepository.save(tweet);
        return tweetMapper.toDTO(tweet);
    }

    @Override
    public List<TweetResponseDTO> findAll() {
        return tweetRepository.findAll().stream().map(tweetMapper::toDTO).toList();
    }

    @Override
    public List<TweetResponseDTO> findAllByUsername(String username) {
        User user = (User) userRepository.findByUsername(username);

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        return tweetRepository.findAllByUser(user).stream().map(tweetMapper::toDTO).toList();
    }

    @Override
    public TweetResponseDTO findById(UUID id) {
        return tweetMapper.toDTO(tweetRepository.findById(id).orElseThrow(() -> new TweetNotFoundException("Tweet not found")));
    }

    @Override
    public TweetResponseDTO update(String token, UUID id, TweetRequestDTO tweetRequestDTO) {
        User user = getUserFromToken(token);

        Tweet tweet = tweetRepository.findById(id).orElseThrow(() -> new TweetNotFoundException("Tweet not found"));

        if (!tweet.getUser().getUsername().equals(user.getUsername())) {
            throw new TweetNotBelongToUserException("User does not belong to this tweet");
        }

        if (tweet.getContent().equals(tweetRequestDTO.content())) {
            throw new ContentIsTheSameException("Tweet content cannot be the same as tweet content");
        }

        tweet.setContent(tweetRequestDTO.content());
        tweet.setUpdatedAt(new Date());

        return tweetMapper.toDTO(tweetRepository.save(tweet));
    }

    @Override
    public void delete(String token, UUID id) {
        User user = getUserFromToken(token);
        Tweet tweet = tweetRepository.findById(id).orElseThrow(() -> new TweetNotFoundException("Tweet not found"));

        if (!tweet.getUser().getUsername().equals(user.getUsername())) {
            throw new TweetNotBelongToUserException("User does not belong to this tweet");
        }

        tweetRepository.delete(tweet);
    }


    private User getUserFromToken(String token) {
        return getUser(token, tokenService, userRepository);
    }
}

