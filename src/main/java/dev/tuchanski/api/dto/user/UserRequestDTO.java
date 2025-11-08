package dev.tuchanski.api.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public record UserRequestDTO(
        @NotEmpty @Length(min = 4, max = 12, message = "Username must be between 4 and 12 characters") String username,
        @NotEmpty(message = "E-mail is mandatory") @Email(message = "E-mail is not valid") String email,
        @NotEmpty(message = "Password is mandatory") @Length(min = 4, max = 20, message = "Password must be between 4 and 20 characters") String password
) {
}
