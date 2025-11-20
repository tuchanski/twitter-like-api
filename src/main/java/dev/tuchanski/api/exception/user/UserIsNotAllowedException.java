package dev.tuchanski.api.exception.user;

public class UserIsNotAllowedException extends RuntimeException {
    public UserIsNotAllowedException(String message) {
        super(message);
    }
}
