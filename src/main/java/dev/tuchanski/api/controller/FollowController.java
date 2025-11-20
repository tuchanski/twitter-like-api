package dev.tuchanski.api.controller;

import dev.tuchanski.api.dto.follow.FollowResponseDTO;
import dev.tuchanski.api.service.follow.FollowService;
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

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Follows", description = "Operations for following and unfollowing users and listing followers/following.")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/users/{usernameFollowTarget}/follow")
    @Operation(summary = "Follow a user", description = "Creates a follow relationship with the target username.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Follow created"),
            @ApiResponse(responseCode = "400", description = "Invalid target"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<FollowResponseDTO> create(@RequestHeader("Authorization") String token, @PathVariable String usernameFollowTarget) {
        token = token.replace("Bearer ", "");
        return ResponseEntity.status(HttpStatus.CREATED).body(followService.createFollow(token, usernameFollowTarget));
    }

    @GetMapping("/users/{username}/following")
    @Operation(summary = "List following", description = "Lists all users the specified username is following.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List returned"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<List<FollowResponseDTO>> getFollowing(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK).body(followService.getFollowing(username));
    }

    @GetMapping("/users/{username}/followers")
    @Operation(summary = "List followers", description = "Lists all users that follow the specified username.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List returned"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<List<FollowResponseDTO>> getFollowers(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK).body(followService.getFollowers(username));
    }

    @DeleteMapping("/users/{usernameUnfollowTarget}/follow")
    @Operation(summary = "Unfollow a user", description = "Removes a follow relationship with the target username.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Unfollowed successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Relationship not found")
    })
    public ResponseEntity<Void> deleteFollow(@RequestHeader("Authorization") String token, @PathVariable String usernameUnfollowTarget) {
        token = token.replace("Bearer ", "");
        followService.delete(token, usernameUnfollowTarget);
        return ResponseEntity.noContent().build();
    }

}