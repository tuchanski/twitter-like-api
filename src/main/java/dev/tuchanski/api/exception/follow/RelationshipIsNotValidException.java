package dev.tuchanski.api.exception.follow;

public class RelationshipIsNotValidException extends RuntimeException {
    public RelationshipIsNotValidException(String message) {
        super(message);
    }
}
