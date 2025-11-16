package dev.tuchanski.api.controller;

import dev.tuchanski.api.dto.tweet.TweetRequestDTO;
import dev.tuchanski.api.dto.tweet.TweetResponseDTO;
import dev.tuchanski.api.service.tweet.TweetService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tweets")
@AllArgsConstructor
public class TweetController {

    private final TweetService tweetService;

    @PostMapping
    public ResponseEntity<TweetResponseDTO> create(@RequestHeader("Authorization") String token, @Valid @RequestBody TweetRequestDTO tweetRequestDTO) {
        token = token.replace("Bearer ", "");
        return ResponseEntity.status(HttpStatus.CREATED).body(tweetService.create(token, tweetRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<TweetResponseDTO>> findAll(@RequestParam(required = false) String username) {

        if (username == null) {
            return ResponseEntity.status(HttpStatus.OK).body(tweetService.findAll());
        }

        return ResponseEntity.status(HttpStatus.OK).body(tweetService.findAllByUsername(username));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TweetResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(tweetService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TweetResponseDTO> update(@RequestHeader("Authorization") String token, @PathVariable UUID id, @Valid @RequestBody TweetRequestDTO tweetRequestDTO) {
        token = token.replace("Bearer ", "");
        return ResponseEntity.status(HttpStatus.OK).body(tweetService.update(token, id, tweetRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TweetResponseDTO> delete(@RequestHeader("Authorization") String token, @PathVariable UUID id) {
        token = token.replace("Bearer ", "");
        tweetService.delete(token, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
