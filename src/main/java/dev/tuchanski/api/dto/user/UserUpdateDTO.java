package dev.tuchanski.api.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public record UserUpdateDTO(
        @Length(min = 1, max = 30, message = "Name must be between 1 and 30 characters") String name,
        @Length(min = 4, max = 12, message = "Username must be between 4 and 12 characters") String username,
        @Email(message = "E-mail is not valid") String email,
        @Length(min = 4, max = 20, message = "Password must be between 4 and 20 characters") String password,
        @NotEmpty @Size(min = 1, max = 256, message = "Bio must be between 1 and 256 characters") String bio
) {
}
