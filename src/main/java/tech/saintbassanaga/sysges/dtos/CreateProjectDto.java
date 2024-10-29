package tech.saintbassanaga.sysges.dtos;

import jakarta.validation.constraints.*;
import tech.saintbassanaga.sysges.models.mapped.ProjectState;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link tech.saintbassanaga.sysges.models.Project}
 */
public record CreateProjectDto(
        @Size(message = "The title size must be contain between 10 - 25 characters", min = 10, max = 25) String titre,
        @Size(min = 50, max = 200) @NotEmpty(message = "Field Could Not be Empty") String description,
        @FutureOrPresent(message = "Project starting date can not be in the past") Date startDate,
        @NotNull @Future(message = "Project due date can not be in the past") Date endDate,
        ProjectState projectState) implements Serializable {
}