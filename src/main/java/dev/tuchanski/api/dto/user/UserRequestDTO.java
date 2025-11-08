package dev.tuchanski.api.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public record UserRequestDTO(
        @NotEmpty @Length(min = 4, max = 12) String username,
        @NotEmpty @Email String email,
        @NotEmpty @Length(min = 4, max = 20) String password
) {
}
