package dev.tuchanski.api.service.comment;

import dev.tuchanski.api.dto.comment.CommentRequestDTO;
import dev.tuchanski.api.dto.comment.CommentResponseDTO;
import dev.tuchanski.api.entity.Comment;
import dev.tuchanski.api.entity.Tweet;
import dev.tuchanski.api.entity.User;
import dev.tuchanski.api.exception.auth.InvalidTokenException;
import dev.tuchanski.api.exception.tweet.TweetNotFoundException;
import dev.tuchanski.api.exception.user.UserNotFoundException;
import dev.tuchanski.api.mapper.CommentMapper;
import dev.tuchanski.api.repository.CommentRepository;
import dev.tuchanski.api.repository.TweetRepository;
import dev.tuchanski.api.repository.UserRepository;
import dev.tuchanski.api.service.auth.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static dev.tuchanski.api.service.user.UserServiceImpl.getUser;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentResponseDTO create(String token, UUID tweetId, CommentRequestDTO commentRequestDTO) {

        User user = getUser(token, tokenService, userRepository);

        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(
                () -> new TweetNotFoundException("Tweet with id: " + tweetId + " not found")
        );

        Comment comment = commentMapper.toEntity(commentRequestDTO);
        comment.setTweet(tweet);
        comment.setUser(user);
        comment = commentRepository.save(comment);

        return commentMapper.toDTO(comment);
    }

    @Override
    public CommentResponseDTO findById(UUID id) {
        return null;
    }

    @Override
    public List<CommentResponseDTO> findByTweetIdOrderByCreatedAtDesc(UUID tweetId) {
        return List.of();
    }

    @Override
    public CommentResponseDTO update(String token, UUID id, CommentRequestDTO commentRequestDTO) {
        return null;
    }

    @Override
    public void delete(String token, UUID id) {

    }

    private User getUserFromToken(String token) {
        return getUser(token, tokenService, userRepository);
    }
}
