package dev.tuchanski.api.controller;

import dev.tuchanski.api.dto.tweet.TweetRequestDTO;
import dev.tuchanski.api.dto.tweet.TweetResponseDTO;
import dev.tuchanski.api.service.tweet.TweetService;
import jakarta.validation.Valid;
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
@RequestMapping("/api/tweets")
@RequiredArgsConstructor
@Tag(name = "Tweets", description = "Endpoints for creating, listing, retrieving, updating and deleting tweets.")
public class TweetController {

    private final TweetService tweetService;

    @PostMapping
    @Operation(summary = "Create tweet", description = "Creates a new tweet for the authenticated user.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tweet created"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<TweetResponseDTO> create(@RequestHeader("Authorization") String token, @Valid @RequestBody TweetRequestDTO tweetRequestDTO) {
        token = token.replace("Bearer ", "");
        return ResponseEntity.status(HttpStatus.CREATED).body(tweetService.create(token, tweetRequestDTO));
    }

    @GetMapping
    @Operation(summary = "List tweets", description = "Lists all tweets or only those from a specific username if provided.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tweets returned")
    })
    public ResponseEntity<List<TweetResponseDTO>> findAll(@RequestParam(required = false) String username) {

        if (username == null) {
            return ResponseEntity.status(HttpStatus.OK).body(tweetService.findAll());
        }

        return ResponseEntity.status(HttpStatus.OK).body(tweetService.findAllByUsername(username));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get tweet by id", description = "Retrieves a specific tweet by its UUID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tweet found"),
            @ApiResponse(responseCode = "404", description = "Tweet not found")
    })
    public ResponseEntity<TweetResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(tweetService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update tweet", description = "Updates the content of an existing tweet.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tweet updated"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Tweet not found")
    })
    public ResponseEntity<TweetResponseDTO> update(@RequestHeader("Authorization") String token, @PathVariable UUID id, @Valid @RequestBody TweetRequestDTO tweetRequestDTO) {
        token = token.replace("Bearer ", "");
        return ResponseEntity.status(HttpStatus.OK).body(tweetService.update(token, id, tweetRequestDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete tweet", description = "Deletes a tweet owned by the authenticated user.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Tweet deleted"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Tweet not found")
    })
    public ResponseEntity<TweetResponseDTO> delete(@RequestHeader("Authorization") String token, @PathVariable UUID id) {
        token = token.replace("Bearer ", "");
        tweetService.delete(token, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}