package dev.tuchanski.api.controller;

import dev.tuchanski.api.dto.user.UserRequestDTO;
import dev.tuchanski.api.dto.user.UserResponseDTO;
import dev.tuchanski.api.dto.user.UserUpdateDTO;
import dev.tuchanski.api.service.user.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(userRequestDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @PreAuthorize("hasRole('ADMIN') or #username == principal.username")
    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponseDTO> findByUsername(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findByUsername(username));
    }

    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(id, userUpdateDTO));
    }

    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
