package dev.tuchanski.api.exception.tweet;

public class TweetNotBelongToUserException extends RuntimeException {
    public TweetNotBelongToUserException(String message) {
        super(message);
    }
}
