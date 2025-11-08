package dev.tuchanski.api.mapper;

import dev.tuchanski.api.dto.user.UserRequestDTO;
import dev.tuchanski.api.dto.user.UserResponseDTO;
import dev.tuchanski.api.dto.user.UserUpdateDTO;
import dev.tuchanski.api.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequestDTO dto) {
        User user = new User();

        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(dto.password());

        return user;
    }

    public User toEntity(UserUpdateDTO dto) {
        User user = new User();

        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(dto.password());

        return user;
    }

    public UserResponseDTO toDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getBio(),
                user.getCreatedAt()
        );

        return dto;
    }

}
