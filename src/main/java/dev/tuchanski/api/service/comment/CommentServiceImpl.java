package dev.tuchanski.api.service.comment;

import dev.tuchanski.api.dto.comment.CommentRequestDTO;
import dev.tuchanski.api.dto.comment.CommentResponseDTO;
import dev.tuchanski.api.entity.Comment;
import dev.tuchanski.api.entity.Tweet;
import dev.tuchanski.api.entity.User;
import dev.tuchanski.api.exception.comment.CommentNotBelongToUserException;
import dev.tuchanski.api.exception.comment.CommentNotFoundException;
import dev.tuchanski.api.exception.tweet.ContentIsTheSameException;
import dev.tuchanski.api.exception.tweet.TweetNotFoundException;
import dev.tuchanski.api.mapper.CommentMapper;
import dev.tuchanski.api.repository.CommentRepository;
import dev.tuchanski.api.repository.TweetRepository;
import dev.tuchanski.api.repository.UserRepository;
import dev.tuchanski.api.service.auth.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static dev.tuchanski.api.service.user.UserServiceImpl.getUser;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    @Transactional
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
    @Transactional(readOnly = true)
    public CommentResponseDTO findById(UUID id) {
        return commentMapper.toDTO(commentRepository.findById(id).orElseThrow(
                () -> new CommentNotFoundException("Comment with id: " + id + " not found")
        ));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDTO> findByTweetIdOrderByCreatedAtDesc(UUID tweetId) {
        if (tweetRepository.findById(tweetId).isEmpty()) {
            throw new TweetNotFoundException("Tweet with id: " + tweetId + " not found");
        }

        return commentRepository.findByTweetIdOrderByCreatedAtDesc(tweetId).stream().map(commentMapper::toDTO).toList();
    }

    @Override
    @Transactional
    public CommentResponseDTO update(String token, UUID id, CommentRequestDTO commentRequestDTO) {
        User user = getUser(token, tokenService, userRepository);

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CommentNotFoundException("Comment with id: " + id + " not found")
        );

        if (!comment.getUser().getUsername().equals(user.getUsername())) {
            throw new CommentNotBelongToUserException("Comment with id: " + id + " not belong to this User");
        }

        if (comment.getContent().equals(commentRequestDTO.content())) {
            throw new ContentIsTheSameException("Comment content is the same");
        }

        comment.setContent(commentRequestDTO.content());
        comment = commentRepository.save(comment);
        return commentMapper.toDTO(comment);
    }

    @Override
    @Transactional
    public void delete(String token, UUID id) {
        User user = getUser(token, tokenService, userRepository);
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CommentNotFoundException("Comment with id: " + id + " not found")
        );

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new CommentNotBelongToUserException("Comment not belong to this User");
        }

        commentRepository.delete(comment);
    }

    private User getUserFromToken(String token) {
        return getUser(token, tokenService, userRepository);
    }
}
