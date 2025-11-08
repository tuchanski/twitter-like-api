package dev.tuchanski.api.dto.user;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;

public record UserUpdateDTO(
        @Length(min = 4, max = 12, message = "Username must be between 4 and 12 characters") String username,
        @Email(message = "E-mail is not valid") String email,
        @Length(min = 4, max = 20, message = "Password must be between 4 and 20 characters") String password
) {
}
