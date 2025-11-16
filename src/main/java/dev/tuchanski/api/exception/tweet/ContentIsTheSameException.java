package dev.tuchanski.api.exception.tweet;

public class ContentIsTheSameException extends RuntimeException {
    public ContentIsTheSameException(String message) {
        super(message);
    }
}
