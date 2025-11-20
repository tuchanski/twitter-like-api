package dev.tuchanski.api.controller;

import dev.tuchanski.api.dto.like.LikeResponseDTO;
import dev.tuchanski.api.service.like.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/tweets/{tweetId}/like")
    public ResponseEntity<LikeResponseDTO> create(@RequestHeader("Authorization") String token, @PathVariable UUID tweetId) {
        token = token.replace("Bearer ", "");
        return ResponseEntity.status(HttpStatus.CREATED).body(likeService.create(token, tweetId));
    }

    @GetMapping("/likes/{likeId}")
    public ResponseEntity<LikeResponseDTO> findById(@PathVariable UUID likeId) {
        return ResponseEntity.status(HttpStatus.OK).body(likeService.findById(likeId));
    }

    @GetMapping("/tweets/{tweetId}/like")
    public ResponseEntity<List<LikeResponseDTO>> findAllByTweet(@PathVariable UUID tweetId) {
        return ResponseEntity.status(HttpStatus.OK).body(likeService.findAllByTweet(tweetId));
    }

    @GetMapping("/users/{userId}/like")
    public ResponseEntity<List<LikeResponseDTO>> findAllByUser(@PathVariable UUID userId) {
        return ResponseEntity.status(HttpStatus.OK).body(likeService.findAllByUser(userId));
    }

    @DeleteMapping("/tweets/{tweetId}/like")
    public ResponseEntity<Void>  delete(@RequestHeader("Authorization") String token, @PathVariable UUID tweetId) {
        token = token.replace("Bearer ", "");
        likeService.deleteById(token, tweetId);
        return ResponseEntity.ok().build();
    }

}
