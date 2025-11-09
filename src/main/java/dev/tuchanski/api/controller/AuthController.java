package dev.tuchanski.api.controller;

import dev.tuchanski.api.dto.auth.LoginRequestDTO;
import dev.tuchanski.api.dto.auth.LoginResponseDTO;
import dev.tuchanski.api.entity.User;
import dev.tuchanski.api.repository.UserRepository;
import dev.tuchanski.api.security.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO body) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(body.email(), body.password())
        );

        User user = userRepository.findByEmail(body.email()).get();
        String token = jwtUtil.generateToken(user.getEmail(), user.getId().toString());

        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDTO(token, user.getId(), user.getEmail()));
    }
}