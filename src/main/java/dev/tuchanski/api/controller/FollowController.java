package dev.tuchanski.api.controller;

import dev.tuchanski.api.dto.follow.FollowResponseDTO;
import dev.tuchanski.api.service.follow.FollowService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/users/{usernameFollowTarget}/follow")
    public ResponseEntity<FollowResponseDTO> create(@RequestHeader("Authorization") String token, @PathVariable String usernameFollowTarget) {
        token = token.replace("Bearer ", "");
        return ResponseEntity.status(HttpStatus.CREATED).body(followService.createFollow(token, usernameFollowTarget));
    }

    @GetMapping("/users/{username}/following")
    public ResponseEntity<List<FollowResponseDTO>> getFollowing(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK).body(followService.getFollowing(username));
    }

    @GetMapping("/users/{username}/followers")
    public ResponseEntity<List<FollowResponseDTO>> getFollowers(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK).body(followService.getFollowers(username));
    }

    @DeleteMapping("/users/{usernameUnfollowTarget}/follow")
    public ResponseEntity<Void> deleteFollow(@RequestHeader("Authorization") String token, @PathVariable String usernameUnfollowTarget) {
        token = token.replace("Bearer ", "");
        followService.delete(token, usernameUnfollowTarget);
        return ResponseEntity.noContent().build();
    }

}
