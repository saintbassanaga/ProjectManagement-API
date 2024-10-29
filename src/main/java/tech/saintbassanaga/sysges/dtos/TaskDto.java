package tech.saintbassanaga.sysges.dtos;

import tech.saintbassanaga.sysges.models.mapped.TaskState;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link tech.saintbassanaga.sysges.models.Task}
 */
/**
 * DTO for {@link tech.saintbassanaga.sysges.repository.Task}
 */
public record TaskDto(String projectTitre, String title, Date dueDate, TaskState state) implements Serializable {
}