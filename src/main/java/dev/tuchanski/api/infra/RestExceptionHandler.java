package dev.tuchanski.api.infra;

import dev.tuchanski.api.exception.auth.InvalidTokenException;
import dev.tuchanski.api.exception.comment.CommentNotBelongToUserException;
import dev.tuchanski.api.exception.comment.CommentNotFoundException;
import dev.tuchanski.api.exception.follow.RelationshipAlreadyExistsException;
import dev.tuchanski.api.exception.follow.RelationshipIsNotValidException;
import dev.tuchanski.api.exception.follow.RelationshipNotFoundException;
import dev.tuchanski.api.exception.like.LikeAlreadyRegisteredException;
import dev.tuchanski.api.exception.like.LikeNotFoundException;
import dev.tuchanski.api.exception.tweet.ContentIsTheSameException;
import dev.tuchanski.api.exception.tweet.TweetNotBelongToUserException;
import dev.tuchanski.api.exception.tweet.TweetNotFoundException;
import dev.tuchanski.api.exception.user.UserAlreadyRegisteredException;
import dev.tuchanski.api.exception.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    // AUTH

    @ExceptionHandler(InvalidTokenException.class)
    private ResponseEntity<RestErrorMessage> invalidTokenExceptionHandler(InvalidTokenException e) {
        RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.UNAUTHORIZED, e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(treatedResponse);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    private ResponseEntity<RestErrorMessage> usernameNotFoundExceptionHandler(UsernameNotFoundException e) {
        RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(treatedResponse);
    }

    // USER

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<RestErrorMessage> userNotFoundHandler(UserNotFoundException e) {
        RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(treatedResponse);
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    private ResponseEntity<RestErrorMessage> userAlreadyRegisteredHandler(UserAlreadyRegisteredException e) {
        RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(treatedResponse);
    }

    // TWEET

    @ExceptionHandler(TweetNotFoundException.class)
    private ResponseEntity<RestErrorMessage> tweetNotFoundExceptionHandler(TweetNotFoundException e) {
        RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(treatedResponse);
    }

    @ExceptionHandler(TweetNotBelongToUserException.class)
    private ResponseEntity<RestErrorMessage> tweetNotBelongToUserExceptionHandler(TweetNotBelongToUserException e) {
        RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(treatedResponse);
    }

    @ExceptionHandler(ContentIsTheSameException.class)
    private ResponseEntity<RestErrorMessage> contentIsTheSameExceptionHandler(ContentIsTheSameException e) {
        RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(treatedResponse);
    }

    // COMMENT

    @ExceptionHandler(CommentNotBelongToUserException.class)
    private ResponseEntity<RestErrorMessage> commentNotBelongToUserException(CommentNotBelongToUserException e) {
        RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(treatedResponse);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    private ResponseEntity<RestErrorMessage> commentNotFoundExceptionHandler(CommentNotFoundException e) {
        RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(treatedResponse);
    }

    // LIKE

    @ExceptionHandler(LikeAlreadyRegisteredException.class)
    private ResponseEntity<RestErrorMessage> likeAlreadyRegisteredExceptionHandler(LikeAlreadyRegisteredException e) {
        RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(treatedResponse);
    }

    @ExceptionHandler(LikeNotFoundException.class)
    private ResponseEntity<RestErrorMessage> likeNotFoundExceptionHandler(LikeNotFoundException e) {
        RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(treatedResponse);
    }

    // FOLLOW

    @ExceptionHandler(RelationshipAlreadyExistsException.class)
    private ResponseEntity<RestErrorMessage> relationshipAlreadyExistsExceptionHandler(RelationshipAlreadyExistsException e) {
        RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(treatedResponse);
    }

    @ExceptionHandler(RelationshipIsNotValidException.class)
    private ResponseEntity<RestErrorMessage> relationshipIsNotValidExceptionHandler(RelationshipIsNotValidException e) {
        RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(treatedResponse);
    }

    @ExceptionHandler(RelationshipNotFoundException.class)
    private ResponseEntity<RestErrorMessage> relationshipNotFoundExceptionHandler(RelationshipNotFoundException e) {
        RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(treatedResponse);
    }

}
