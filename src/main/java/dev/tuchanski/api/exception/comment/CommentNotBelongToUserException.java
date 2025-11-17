package dev.tuchanski.api.exception.comment;

public class CommentNotBelongToUserException extends RuntimeException {
    public CommentNotBelongToUserException(String message) {
        super(message);
    }
}
