package dev.tuchanski.api.dto.user;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;

public record UserUpdateDTO(
        @Length(min = 4, max = 12) String username,
        @Email String email,
        @Length(min = 4, max = 20) String password
) {
}
