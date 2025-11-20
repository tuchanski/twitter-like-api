package dev.tuchanski.api.service.user;

import dev.tuchanski.api.dto.user.UserRequestDTO;
import dev.tuchanski.api.dto.user.UserResponseDTO;
import dev.tuchanski.api.dto.user.UserUpdateDTO;
import dev.tuchanski.api.entity.User;
import dev.tuchanski.api.entity.enums.UserRole;
import dev.tuchanski.api.exception.auth.InvalidTokenException;
import dev.tuchanski.api.exception.user.UserAlreadyRegisteredException;
import dev.tuchanski.api.exception.user.UserIsNotAllowedException;
import dev.tuchanski.api.exception.user.UserNotFoundException;
import dev.tuchanski.api.mapper.UserMapper;
import dev.tuchanski.api.repository.UserRepository;
import dev.tuchanski.api.service.auth.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Override
    public UserResponseDTO create(UserRequestDTO userRequestDTO) {

        if (userRepository.existsByEmail(userRequestDTO.email())) {
            throw new UserAlreadyRegisteredException(String.format("User with email %s already exists", userRequestDTO.email()));
        }

        if (userRepository.existsByUsername(userRequestDTO.username())) {
            throw new UserAlreadyRegisteredException(String.format("User with name %s already exists", userRequestDTO.username()));
        }

        User user = userMapper.toEntity(userRequestDTO);
        user.setPassword(passwordEncoder.encode(userRequestDTO.password()));

        UserRole role;

        if (userRepository.findAll().isEmpty()) {
            role = UserRole.ADMIN; // First user is Admin :)
        } else {
            role = UserRole.USER;
        }

        user.setRole(role);
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Override
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream().map(
                userMapper::toDTO
        ).toList();
    }

    @Override
    public UserResponseDTO findById(UUID id) {
        return userMapper.toDTO(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found")));
    }

    @Override
    public UserResponseDTO findByUsername(String username) {
        User user = (User) userRepository.findByUsername(username);

        if (user == null) {
            throw new UserNotFoundException("User with username " + username + " not found");
        }

        return userMapper.toDTO(user);
    }

    @Override
    public UserResponseDTO update(UUID id, UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));

        if (userUpdateDTO.name() != null) {
            user.setUsername(userUpdateDTO.name());
        }

        if (userUpdateDTO.email() != null) {
            if (userRepository.existsByEmail(userUpdateDTO.email())) {
                throw new UserAlreadyRegisteredException("User with " + userUpdateDTO.email() + " already registered");
            }
            user.setEmail(userUpdateDTO.email());
        }

        if (userUpdateDTO.username() != null) {
            if (userRepository.existsByUsername(userUpdateDTO.username())) {
                throw new UserAlreadyRegisteredException("User with " + userUpdateDTO.username() + " already registered");
            }
            user.setUsername(userUpdateDTO.username());
        }

        if (userUpdateDTO.password() != null) {
            user.setPassword(passwordEncoder.encode(userUpdateDTO.password()));
        }

        if (userUpdateDTO.bio() != null) {
            user.setBio(userUpdateDTO.bio());
        }

        user = userRepository.save(user);

        return userMapper.toDTO(user);
    }

    @Override
    public UserResponseDTO addAdmin(String usernameTarget) {
        User targetUser = (User) userRepository.findByUsername(usernameTarget);

        if (targetUser == null) {
            throw new UserNotFoundException("User with username " + usernameTarget + " not found");
        }

        targetUser.setRole(UserRole.ADMIN);
        targetUser = userRepository.save(targetUser);

        return userMapper.toDTO(targetUser);
    }

    @Override
    public void delete(UUID id) {

        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }

        userRepository.deleteById(id);

    }

    private User getUserFromToken(String token) {
        return getUser(token, tokenService, userRepository);
    }

    public static User getUser(String token, TokenService tokenService, UserRepository userRepository) {
        String username = tokenService.validateToken(token);
        if (username == null || username.isEmpty()) {
            throw new InvalidTokenException("Invalid token");
        }

        User user = (User) userRepository.findByUsername(username);

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        return user;
    }
}
