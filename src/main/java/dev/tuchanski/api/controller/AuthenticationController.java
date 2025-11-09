package dev.tuchanski.api.controller;

import dev.tuchanski.api.dto.auth.LoginRequestDTO;
import dev.tuchanski.api.dto.auth.LoginResponseDTO;
import dev.tuchanski.api.dto.user.UserRequestDTO;
import dev.tuchanski.api.dto.user.UserResponseDTO;
import dev.tuchanski.api.entity.User;
import dev.tuchanski.api.service.auth.TokenService;
import dev.tuchanski.api.service.user.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequestDTO.username(), loginRequestDTO.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());
        var id = ((User) auth.getPrincipal()).getId();
        var username = ((User) auth.getPrincipal()).getUsername();

        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDTO(token, id, username));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO registerRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(registerRequestDTO));
    }


}
