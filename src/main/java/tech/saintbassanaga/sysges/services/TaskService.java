package tech.saintbassanaga.sysges.services;


import org.springframework.stereotype.Service;
import tech.saintbassanaga.sysges.dtos.TaskDto;
import tech.saintbassanaga.sysges.models.Task;

import java.util.List;
import java.util.UUID;

/**
 * Created by saintbassanaga
 * In the Project SysGes at Sat - 11/9/24
 */

@Service
public interface TaskService {
    public Task createTask(UUID projectUuid , TaskDto createTaskDto);
    public Task update(UUID taskUuid, TaskDto updateTaskDto);
    public List<TaskDto> findAll();
    public TaskDto findByUuid(UUID uuid);
    public void delete(UUID uuid);
    public List<TaskDto> findTaskCompletedByUser(UUID uuid);
}
