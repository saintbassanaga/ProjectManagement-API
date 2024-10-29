package tech.saintbassanaga.sysges.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import tech.saintbassanaga.sysges.models.mapped.Role;

import java.io.Serializable;

/**
 * DTO for {@link tech.saintbassanaga.sysges.models.User}
 */
public record ShortUserDto(String surname, @NotNull @Size(min = 6, max = 15) String username, String email,
                           Role role) implements Serializable {
}