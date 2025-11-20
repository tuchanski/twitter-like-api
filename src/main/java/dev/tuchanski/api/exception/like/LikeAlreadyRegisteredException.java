package dev.tuchanski.api.exception.like;

public class LikeAlreadyRegisteredException extends RuntimeException {
    public LikeAlreadyRegisteredException(String message) {
        super(message);
    }
}
