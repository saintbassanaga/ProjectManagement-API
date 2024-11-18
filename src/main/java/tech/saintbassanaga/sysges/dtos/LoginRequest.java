package tech.saintbassanaga.sysges.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link tech.saintbassanaga.sysges.models.User}
 */
public record LoginRequest(@NotNull @Size(min = 6, max = 15) String username, @NotNull @Email String email,
                           @NotNull @Size(min = 8, max = 100) String password) implements Serializable {
}