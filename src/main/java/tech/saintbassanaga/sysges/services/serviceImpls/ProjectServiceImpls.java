package tech.saintbassanaga.sysges.services.serviceImpls;

import org.springframework.stereotype.Service;
import tech.saintbassanaga.sysges.dtos.*;
import tech.saintbassanaga.sysges.models.Project;
import tech.saintbassanaga.sysges.models.Task;
import tech.saintbassanaga.sysges.repository.ProjectRepository;
import tech.saintbassanaga.sysges.repository.TaskRepository;
import tech.saintbassanaga.sysges.repository.UserRepository;
import tech.saintbassanaga.sysges.services.ProjectService;
import tech.saintbassanaga.sysges.services.TaskService;
import tech.saintbassanaga.sysges.services.specificationService.TaskSearchService;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of the ProjectService interface for managing Project-related operations.
 * Provides methods for creating, updating, retrieving, and deleting Project entities.
 * This service layer interacts with Project and User repositories.
 * <p>
 * Created by saintbassanaga
 * In the Project SysGes at Tue - 10/29/24
 */

@Service
public class ProjectServiceImpls implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskService taskService;
    private final TaskRepository taskRepository;
    private final TaskSearchService taskSearchService;

    /**
     * Constructs a new ProjectServiceImpls with necessary dependencies.
     *
     * @param projectRepository the repository for managing Project entities
     * @param userRepository    the repository for managing User entities
     */
    public ProjectServiceImpls(ProjectRepository projectRepository, UserRepository userRepository, TaskService taskService, TaskRepository taskRepository,
                               TaskSearchService taskSearchService) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.taskService = taskService;
        this.taskRepository = taskRepository;
        this.taskSearchService = taskSearchService;
    }

    /**
     * Creates a new Project based on the provided CreateProjectDto.
     *
     * @param createProjectDto the DTO containing data for creating a new project
     * @return a confirmation message upon successful creation, including the project UUID
     */
    @Override
    public String createProject(CreateProjectDto createProjectDto) {
        Project project = DtoMapper.creationProject(createProjectDto);
        projectRepository.save(project);
        return "Project successfully created with UUID: " + project.getUuid();
    }

    /**
     * Updates an existing Project with the provided data.
     *
     * @param userUuid         the UUID of the User performing the update
     * @param updateProjectDto the DTO containing updated project information
     * @return the updated Project entity
     */
    @Override
    public Project update(UUID userUuid, UpdateProjectDto updateProjectDto) {
        Project project = new Project();
        project.setTitre(updateProjectDto.title());
        project.setDescription(updateProjectDto.description());
        project.setProjectState(updateProjectDto.projectState());
        project.setStartDate(updateProjectDto.startDate());
        project.setEndDate(updateProjectDto.endDate());

        // Update users associated with the project, if provided
        if (updateProjectDto.users() != null) {
            project.setUsers(updateProjectDto.users().stream().map(userRepository::getReferenceById).collect(Collectors.toSet()));
        }
        return projectRepository.save(project);
    }

    /**
     * Retrieves a list of all Projects in a short format.
     *
     * @return a list of ShortProjectDto containing summarized information for each Project
     */
    @Override
    public List<ShortProjectDto> findAll() {
        return projectRepository.findAllProjectUsers();
    }

    /**
     * Finds a Project by its UUID.
     *
     * @param uuid the UUID of the Project to be found
     * @return a ShowProjectDto containing detailed information of the found Project
     * @throws RuntimeException if the Project is not found
     */
    @Override
    public ShowProjectDto findByUuid(UUID uuid) {
        Project project = projectRepository.findById(uuid).orElseThrow(() -> new RuntimeException("Not Found"));
        return DtoMapper.showProjectDto(project);
    }

    /**
     * Deletes a Project by its UUID.
     *
     * @param uuid the UUID of the Project to be deleted
     */
    @Override
    public void deleteByUser(UUID uuid) {
        projectRepository.deleteById(uuid);
    }

    /**
     * @param projectUuid n
     * @return message
     */
    @Override
    public String addTask(UUID projectUuid, TaskDto taskDto) {
        Project referenceById = projectRepository.getReferenceById(projectUuid);
        Task response = taskService.createTask(projectUuid,taskDto);
        referenceById.setTasks(Set.of(response));
        return "Task created Successfully with id : " + response.getUuid();
    }

    @Override
    public String deleteTask(UUID projectUuid, UUID taskUUID) {
        taskService.delete(
                taskRepository
                        .findOne(
                                taskSearchService.findTaskByProjectTaskUuid(projectUuid, taskUUID)
                        )
                        .orElseThrow(() -> new RuntimeException("Not Found Task"))
                        .getUuid()
        );

        return "task delete successfully";
    }
}
