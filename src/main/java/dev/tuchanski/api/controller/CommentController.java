package dev.tuchanski.api.controller;

import dev.tuchanski.api.dto.comment.CommentRequestDTO;
import dev.tuchanski.api.dto.comment.CommentResponseDTO;
import dev.tuchanski.api.service.comment.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/tweets/{tweetId}/comments")
    public ResponseEntity<CommentResponseDTO> create(@RequestHeader("Authorization") String token,
                                                     @PathVariable UUID tweetId,
                                                        @RequestBody CommentRequestDTO commentRequestDTO) {

        token = token.replace("Bearer ", "");
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.create(token, tweetId, commentRequestDTO));
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.findById(id));
    }

    @GetMapping("/tweets/{tweetId}/comments")
    public ResponseEntity<List<CommentResponseDTO>> findByTweetId(@PathVariable UUID tweetId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.findByTweetIdOrderByCreatedAtDesc(tweetId));
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDTO> update(@RequestHeader("Authorization") String token,
                                                     @PathVariable UUID id,
                                                     @RequestBody CommentRequestDTO commentRequestDTO) {
        token = token.replace("Bearer ", "");
        return ResponseEntity.status(HttpStatus.OK).body(commentService.update(token, id, commentRequestDTO));
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> delete(@RequestHeader("Authorization") String token, @PathVariable UUID id) {
        token = token.replace("Bearer ", "");
        commentService.delete(token, id);
        return ResponseEntity.noContent().build();
    }

}
