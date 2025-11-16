package dev.tuchanski.api.infra;

import dev.tuchanski.api.exception.auth.InvalidTokenException;
import dev.tuchanski.api.exception.tweet.ContentIsTheSameException;
import dev.tuchanski.api.exception.tweet.TweetNotBelongToUserException;
import dev.tuchanski.api.exception.tweet.TweetNotFoundException;
import dev.tuchanski.api.exception.user.UserAlreadyRegisteredException;
import dev.tuchanski.api.exception.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<RestErrorMessage> userNotFoundHandler(UserNotFoundException e) {
        RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(treatedResponse);
    }

    @ExceptionHandler(TweetNotFoundException.class)
    private ResponseEntity<RestErrorMessage> tweetNotFoundExceptionHandler(TweetNotFoundException e) {
        RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(treatedResponse);
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    private ResponseEntity<RestErrorMessage> userAlreadyRegisteredHandler(UserAlreadyRegisteredException e) {
        RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(treatedResponse);
    }

    @ExceptionHandler(InvalidTokenException.class)
    private ResponseEntity<RestErrorMessage> invalidTokenExceptionHandler(InvalidTokenException e) {
        RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.UNAUTHORIZED, e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(treatedResponse);
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

}
