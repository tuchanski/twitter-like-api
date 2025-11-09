package dev.tuchanski.api.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public record LoginRequestDTO(
        @NotEmpty(message = "Username is mandatory") @Length(min = 4, max = 12, message = "Username must be between 4 and 12 characters") String username,
        @NotEmpty(message = "Password is mandatory") @Length(min = 4, max = 20, message = "Password must be between 4 and 20 characters") String password
) {
}
