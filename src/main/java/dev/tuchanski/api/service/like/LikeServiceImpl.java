package dev.tuchanski.api.service.like;

import dev.tuchanski.api.dto.like.LikeResponseDTO;
import dev.tuchanski.api.entity.Like;
import dev.tuchanski.api.entity.Tweet;
import dev.tuchanski.api.entity.User;
import dev.tuchanski.api.exception.like.LikeAlreadyRegisteredException;
import dev.tuchanski.api.exception.like.LikeNotFoundException;
import dev.tuchanski.api.exception.tweet.TweetNotFoundException;
import dev.tuchanski.api.exception.user.UserNotFoundException;
import dev.tuchanski.api.mapper.LikeMapper;
import dev.tuchanski.api.repository.LikeRepository;
import dev.tuchanski.api.repository.TweetRepository;
import dev.tuchanski.api.repository.UserRepository;
import dev.tuchanski.api.service.auth.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static dev.tuchanski.api.service.user.UserServiceImpl.getUser;

@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;
    private final TokenService tokenService;
    private final LikeMapper likeMapper;

    @Override
    @Transactional
    public LikeResponseDTO create(String token, UUID tweetId) {
        User user = getUserFromToken(token);

        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(
                () -> new TweetNotFoundException("Tweet not found")
        );

        if (likeRepository.existsByUserAndTweet(user, tweet)) {
            throw new LikeAlreadyRegisteredException("Tweet is already liked by this user");
        }

        Like like = new Like();
        like.setUser(user);
        like.setTweet(tweet);
        like = likeRepository.save(like);

        return likeMapper.toDTO(like);
    }

    @Override
    @Transactional(readOnly = true)
    public LikeResponseDTO findById(UUID id) {
        return likeMapper.toDTO(likeRepository.findById(id).orElseThrow(
                () -> new LikeNotFoundException("Like not found")
        ));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LikeResponseDTO> findAllByTweet(UUID tweetId) {
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(
                () -> new TweetNotFoundException("Tweet not found")
        );

        return likeRepository.findAllByTweet(tweet).stream().map(likeMapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LikeResponseDTO> findAllByUser(String username) {
        User user = (User) userRepository.findByUsername(username);

        if (user == null) {
            throw new UserNotFoundException("User with name: " + username + " not found");
        }

        return likeRepository.findAllByUser(user).stream().map(likeMapper::toDTO).toList();
    }

    @Override
    @Transactional
    public void deleteById(String token, UUID tweetId) {
        User user = getUserFromToken(token);
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(
                () -> new TweetNotFoundException("Tweet not found")
        );

        Like like = likeRepository.findByUserAndTweet(user, tweet);

        if (like == null) {
            throw new LikeNotFoundException("Tweet is not liked by this user");
        }

        likeRepository.delete(like);
    }

    private User getUserFromToken(String token) {
        return getUser(token, tokenService, userRepository);
    }
}
