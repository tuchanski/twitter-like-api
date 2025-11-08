package dev.tuchanski.api.service.user;

import dev.tuchanski.api.dto.user.UserRequestDTO;
import dev.tuchanski.api.dto.user.UserResponseDTO;
import dev.tuchanski.api.dto.user.UserUpdateDTO;
import dev.tuchanski.api.entity.User;
import dev.tuchanski.api.exceptions.user.UserAlreadyRegisteredException;
import dev.tuchanski.api.exceptions.user.UserNotFoundException;
import dev.tuchanski.api.mapper.UserMapper;
import dev.tuchanski.api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDTO create(UserRequestDTO userRequestDTO) {

        if (userRepository.existsByEmail(userRequestDTO.email())) {
            throw new UserAlreadyRegisteredException(String.format("User with email %s already exists", userRequestDTO.email()));
        }

        if (userRepository.existsByUsername(userRequestDTO.username())) {
            throw new UserAlreadyRegisteredException(String.format("User with name %s already exists", userRequestDTO.username()));
        }

        User user = userMapper.toEntity(userRequestDTO);
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
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UserNotFoundException("User with name " + username + " not found");
        }

        return userMapper.toDTO(user);
    }

    @Override
    public UserResponseDTO update(UUID id, UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));

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
            // Fazer hash da senha dps
            user.setPassword(userUpdateDTO.password());
        }

        user = userRepository.save(user);

        return userMapper.toDTO(user);
    }

    @Override
    public void delete(UUID id) {

        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }

        userRepository.deleteById(id);

    }
}
