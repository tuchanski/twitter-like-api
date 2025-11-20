package dev.tuchanski.api.controller;

import dev.tuchanski.api.dto.like.LikeResponseDTO;
import dev.tuchanski.api.service.like.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/tweets/{tweetId}/likes")
    public ResponseEntity<LikeResponseDTO> create(@RequestHeader("Authorization") String token, @PathVariable UUID tweetId) {
        token = token.replace("Bearer ", "");
        return ResponseEntity.status(HttpStatus.CREATED).body(likeService.create(token, tweetId));
    }

    @GetMapping("/likes/{likeId}")
    public ResponseEntity<LikeResponseDTO> findById(@PathVariable UUID likeId) {
        return ResponseEntity.status(HttpStatus.OK).body(likeService.findById(likeId));
    }

    @GetMapping("/tweets/{tweetId}/likes")
    public ResponseEntity<List<LikeResponseDTO>> findAllByTweet(@PathVariable UUID tweetId) {
        return ResponseEntity.status(HttpStatus.OK).body(likeService.findAllByTweet(tweetId));
    }

    @GetMapping("/users/{username}/likes")
    public ResponseEntity<List<LikeResponseDTO>> findAllByUser(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK).body(likeService.findAllByUser(username));
    }

    @DeleteMapping("/tweets/{tweetId}/likes")
    public ResponseEntity<Void>  delete(@RequestHeader("Authorization") String token, @PathVariable UUID tweetId) {
        token = token.replace("Bearer ", "");
        likeService.deleteById(token, tweetId);
        return ResponseEntity.ok().build();
    }

}
