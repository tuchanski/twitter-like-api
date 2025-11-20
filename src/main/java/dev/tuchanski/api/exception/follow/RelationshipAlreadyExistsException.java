package dev.tuchanski.api.exception.follow;

public class RelationshipAlreadyExistsException extends RuntimeException {
    public RelationshipAlreadyExistsException(String message) {
        super(message);
    }
}
