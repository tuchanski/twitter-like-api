package dev.tuchanski.api.mapper;

import dev.tuchanski.api.dto.comment.CommentRequestDTO;
import dev.tuchanski.api.dto.comment.CommentResponseDTO;
import dev.tuchanski.api.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public CommentResponseDTO toDTO(Comment comment) {
        return new CommentResponseDTO(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                comment.getUser().getUsername(),
                comment.getTweet().getId()
        );
    }

    public Comment toEntity(CommentRequestDTO commentRequestDTO) {
        Comment comment = new Comment();
        comment.setContent(commentRequestDTO.content());
        return comment;
    }

}
