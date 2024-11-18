package tech.saintbassanaga.sysges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import tech.saintbassanaga.sysges.dtos.TaskDto;
import tech.saintbassanaga.sysges.models.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID>, JpaSpecificationExecutor<Task> {
    @Query("SELECT new tech.saintbassanaga.sysges.dtos.TaskDto(u.project.uuid, u.title, u.dueDate, u.state, u.description) FROM Task u")
    List<TaskDto> findTasksOrderBySeverity();
    Optional<Task> findTaskByTitle(String title);
    List<Task> findTasksByProjectUuid(UUID uuid);
}