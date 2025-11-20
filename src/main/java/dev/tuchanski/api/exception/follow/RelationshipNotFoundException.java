package dev.tuchanski.api.exception.follow;

public class RelationshipNotFoundException extends RuntimeException {
    public RelationshipNotFoundException(String message) {
        super(message);
    }
}
