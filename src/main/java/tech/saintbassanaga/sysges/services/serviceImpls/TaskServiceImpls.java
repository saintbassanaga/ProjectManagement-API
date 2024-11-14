package tech.saintbassanaga.sysges.services.serviceImpls;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import tech.saintbassanaga.sysges.dtos.DtoMapper;
import tech.saintbassanaga.sysges.dtos.TaskDto;
import tech.saintbassanaga.sysges.models.Task;
import tech.saintbassanaga.sysges.models.mapped.TaskState;
import tech.saintbassanaga.sysges.repository.TaskRepository;
import tech.saintbassanaga.sysges.repository.UserRepository;
import tech.saintbassanaga.sysges.services.TaskService;
import tech.saintbassanaga.sysges.services.specificationService.TaskSearchService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by saintbassanaga
 * In the Project SysGes at Sat - 11/9/24
 */
@Component
public class TaskServiceImpls implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskServiceImpls(TaskSearchService specification, TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    /**
     * @param createTaskDto Task Data Transformed
     * @return successful message
     */
    @Override
    public Task createTask(UUID projectUuid, TaskDto createTaskDto) {
        Task task = DtoMapper
                .fromTaskDto(
                        new TaskDto(projectUuid,
                                createTaskDto.title(),
                                createTaskDto.dueDate(),
                                createTaskDto.state(),
                                createTaskDto.description())
                );
        if (taskRepository.existsById(task.getUuid()) || taskRepository.findTaskByTitle(task.getTitle()).isEmpty())
            return null;
        else
            return taskRepository.save(task);
    }

    /**
     * @param taskUuid      the task to modify  UUID
     * @param updateTaskDto the task Dto that hold new Data
     * @return updated Task
     */
    @Override
    public Task update(UUID taskUuid, TaskDto updateTaskDto) {
        Task foundTask = DtoMapper.fromTaskDto(findByUuid(taskUuid));

        foundTask.setTitle(updateTaskDto.title());
        foundTask.setDescription(updateTaskDto.description());
        foundTask.setDueDate(updateTaskDto.dueDate());

        return taskRepository.save(foundTask);
    }

    /**
     * @return found Task ordered by Severity
     */
    @Override
    public List<TaskDto> findAll() {
        return taskRepository.findTasksOrderBySeverity();
    }

    /**
     * @param uuid task to find UUID
     * @return TaskDto that have this `uuid` has ID
     */
    @Override
    public TaskDto findByUuid(UUID uuid) {
        Task foundTask = taskRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Not Found Task with ID : " + uuid));
        return DtoMapper.toTaskDto(foundTask);
    }

    /**
     * @param uuid task to be delete ID
     */
    @Override
    public void delete(UUID uuid) {
        taskRepository.deleteById(uuid);
    }

    /**
     * @param uuid user that perform task
     * @return the list of task performed the `User` with this `uuid`
     */
    @Override
    public List<TaskDto> findTaskCompletedByUser(UUID uuid) {
        TaskState taskState = TaskState.valueOf("ENDED");

        Specification<Task> taskSpecification = Specification
                .where(TaskSearchService.findTaskWithState(taskState))
                .and(TaskSearchService.completedBy(userRepository.getReferenceById(uuid)));
        return taskRepository.findAll(taskSpecification)
                .stream()
                .map(DtoMapper::toTaskDto)
                .collect(Collectors.toList());
    }
}
