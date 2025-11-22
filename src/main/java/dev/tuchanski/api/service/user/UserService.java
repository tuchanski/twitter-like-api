package dev.tuchanski.api.service.user;

import dev.tuchanski.api.dto.user.UserRequestDTO;
import dev.tuchanski.api.dto.user.UserResponseDTO;
import dev.tuchanski.api.dto.user.UserUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponseDTO create(UserRequestDTO userRequestDTO);
    List<UserResponseDTO> findAll();
    UserResponseDTO findById(UUID id);
    UserResponseDTO findByUsername(String username);
    UserResponseDTO update(UUID id, UserUpdateDTO userUpdateDTO);
    UserResponseDTO addAdmin(UUID id);
    void delete(UUID id);
}
