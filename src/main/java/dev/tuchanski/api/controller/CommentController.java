package dev.tuchanski.api.controller;

import dev.tuchanski.api.dto.comment.CommentRequestDTO;
import dev.tuchanski.api.dto.comment.CommentResponseDTO;
import dev.tuchanski.api.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Comments", description = "Operations for creating, reading, updating and deleting comments on tweets.")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/tweets/{tweetId}/comments")
    @Operation(summary = "Create a comment", description = "Creates a new comment on a tweet.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Comment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<CommentResponseDTO> create(@RequestHeader("Authorization") String token,
                                                     @PathVariable UUID tweetId,
                                                     @RequestBody CommentRequestDTO commentRequestDTO) {

        token = token.replace("Bearer ", "");
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.create(token, tweetId, commentRequestDTO));
    }

    @GetMapping("/comments/{id}")
    @Operation(summary = "Get comment by id", description = "Retrieves a single comment by its UUID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comment found"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    public ResponseEntity<CommentResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.findById(id));
    }

    @GetMapping("/tweets/{tweetId}/comments")
    @Operation(summary = "List comments for tweet", description = "Returns all comments for a tweet ordered by creation time descending.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comments returned"),
            @ApiResponse(responseCode = "404", description = "Tweet not found")
    })
    public ResponseEntity<List<CommentResponseDTO>> findByTweetId(@PathVariable UUID tweetId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.findByTweetIdOrderByCreatedAtDesc(tweetId));
    }

    @PutMapping("/comments/{id}")
    @Operation(summary = "Update a comment", description = "Updates the content of an existing comment.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comment updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    public ResponseEntity<CommentResponseDTO> update(@RequestHeader("Authorization") String token,
                                                     @PathVariable UUID id,
                                                     @RequestBody CommentRequestDTO commentRequestDTO) {
        token = token.replace("Bearer ", "");
        return ResponseEntity.status(HttpStatus.OK).body(commentService.update(token, id, commentRequestDTO));
    }

    @DeleteMapping("/comments/{id}")
    @Operation(summary = "Delete a comment", description = "Deletes a comment by its id.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Comment deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    public ResponseEntity<Void> delete(@RequestHeader("Authorization") String token, @PathVariable UUID id) {
        token = token.replace("Bearer ", "");
        commentService.delete(token, id);
        return ResponseEntity.noContent().build();
    }

}