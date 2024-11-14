package tech.saintbassanaga.sysges.dtos;

import tech.saintbassanaga.sysges.models.mapped.TaskState;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * DTO for {@link tech.saintbassanaga.sysges.models.Task}
 */
public record TaskDto(UUID projectUUID, String title, Date dueDate, TaskState state, String description) implements Serializable {
}