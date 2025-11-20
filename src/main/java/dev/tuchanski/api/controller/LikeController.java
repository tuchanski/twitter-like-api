package dev.tuchanski.api.controller;

import dev.tuchanski.api.dto.like.LikeResponseDTO;
import dev.tuchanski.api.service.like.LikeService;
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
@Tag(name = "Likes", description = "Endpoints for liking tweets and listing likes by tweet or user.")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/tweets/{tweetId}/likes")
    @Operation(summary = "Like a tweet", description = "Registers a like for the specified tweet.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Like created"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Tweet not found")
    })
    public ResponseEntity<LikeResponseDTO> create(@RequestHeader("Authorization") String token, @PathVariable UUID tweetId) {
        token = token.replace("Bearer ", "");
        return ResponseEntity.status(HttpStatus.CREATED).body(likeService.create(token, tweetId));
    }

    @GetMapping("/likes/{likeId}")
    @Operation(summary = "Get like by id", description = "Retrieves a like entry by its UUID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Like found"),
            @ApiResponse(responseCode = "404", description = "Like not found")
    })
    public ResponseEntity<LikeResponseDTO> findById(@PathVariable UUID likeId) {
        return ResponseEntity.status(HttpStatus.OK).body(likeService.findById(likeId));
    }

    @GetMapping("/tweets/{tweetId}/likes")
    @Operation(summary = "List likes of tweet", description = "Lists all likes associated with a specific tweet.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List returned"),
            @ApiResponse(responseCode = "404", description = "Tweet not found")
    })
    public ResponseEntity<List<LikeResponseDTO>> findAllByTweet(@PathVariable UUID tweetId) {
        return ResponseEntity.status(HttpStatus.OK).body(likeService.findAllByTweet(tweetId));
    }

    @GetMapping("/users/{username}/likes")
    @Operation(summary = "List likes by user", description = "Lists all likes performed by the specified user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List returned"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<List<LikeResponseDTO>> findAllByUser(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK).body(likeService.findAllByUser(username));
    }

    @DeleteMapping("/tweets/{tweetId}/likes")
    @Operation(summary = "Remove like from tweet", description = "Removes the like of the authenticated user from the specified tweet.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Like removed"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Like not found")
    })
    public ResponseEntity<Void>  delete(@RequestHeader("Authorization") String token, @PathVariable UUID tweetId) {
        token = token.replace("Bearer ", "");
        likeService.deleteById(token, tweetId);
        return ResponseEntity.ok().build();
    }

}