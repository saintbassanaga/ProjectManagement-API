package tech.saintbassanaga.sysges.services.serviceImpls;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.saintbassanaga.sysges.dtos.*;
import tech.saintbassanaga.sysges.models.User;
import tech.saintbassanaga.sysges.models.mapped.ProjectState;
import tech.saintbassanaga.sysges.repository.ProjectRepository;
import tech.saintbassanaga.sysges.repository.TaskRepository;
import tech.saintbassanaga.sysges.repository.UserRepository;
import tech.saintbassanaga.sysges.services.UserService;
import tech.saintbassanaga.sysges.services.specificationService.UserSpecification;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of the UserService interface for managing User-related operations.
 * Provides methods for creating, updating, and retrieving User entities.
 * This service layer interacts with User, Task, and Project repositories and uses
 * specifications for dynamic query building.
 * <p>
 * Created by saintbassanaga
 * In the Project SysGes at Sat - 10/26/24
 */
@Service
public class UserServiceImpls implements UserService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor to initialize UserServiceImpls with necessary dependencies.
     *
     * @param userSpecification the specification service for building dynamic queries
     * @param userRepository    the repository for User entities
     * @param projectRepository the repository for Project entities
     * @param taskRepository    the repository for Task entities
     */
    public UserServiceImpls(UserSpecification userSpecification, UserRepository userRepository, ProjectRepository projectRepository, TaskRepository taskRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.passwordEncoder = passwordEncoder;
    }


    /**
     * Creates a new User based on the provided UserCreationDto.
     *
     * @param userCreationDto the DTO containing data for creating a new user
     * @return a confirmation message upon successful creation
     */
    @Transactional
    public String createUser(UserCreationDto userCreationDto) {
        User user = DtoMapper.creationUser(userCreationDto);
        user.setPassword(passwordEncoder.encode(userCreationDto.password()));
        userRepository.save(user);
        return "User created successfully with username: " + user.getUsername();
    }

    /**
     * Updates an existing User with the provided data.
     *
     * @param userUuid        the UUID of the User to be updated
     * @param updateUserDto   the DTO containing updated User information
     * @return a ShowUserDto containing updated information of the User
     */
    @Transactional
    public ShowUserDto update(UUID userUuid, UpdateUserDto updateUserDto) {
        User referenceById = userRepository.getReferenceById(userUuid);
        referenceById.setEmail(updateUserDto.email());
        referenceById.setRole(updateUserDto.role());

        if (updateUserDto.projectUuids() != null) {
            referenceById.setProjects(
                    updateUserDto.projectUuids()
                            .stream()
                            .map(projectRepository::getReferenceById)
                            .collect(Collectors.toSet()));
        }

        if (updateUserDto.taskUuids() != null) {
            referenceById.setTasks(
                    updateUserDto.taskUuids()
                            .stream()
                            .map(taskRepository::getReferenceById)
                            .collect(Collectors.toSet())
            );
        }

        User savedUser = userRepository.save(referenceById);

        return DtoMapper.showUserDto(savedUser);
    }

    /**
     * Retrieves all Users in a short format.
     *
     * @return a list of ShortUserDto containing summarized information for each User
     */
    @Override
    public List<ShortUserDto> findAll() {
        return userRepository.findAllShortUsers();
    }

    /**
     * Finds a User by their UUID.
     *
     * @param uuid the UUID of the User to be found
     * @return a ShowUserDto containing detailed information of the found User
     * @throws RuntimeException if the User is not found
     */
    @Override
    @Cacheable(value = "users", key = "#uuid")
    public ShowUserDto findByUuid(UUID uuid) {
        User foundUser = userRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Not Found"));
        return DtoMapper.showUserDto(foundUser);
    }

    /**
     * Retrieves Users with tasks matching specified criteria such as task status,
     * user role, and location.
     *
     * @param status   the status of tasks (e.g., "running") to filter Users by
     * @param role     the role of Users to filter by
     * @param location the location of Users to filter by
     * @return a list of ShortUserDto containing summarized information for matching Users
     */
    @Transactional(readOnly = true)
    public List<ShortUserDto> findUserWithTaskRunning(String state, String role, String location) {

        // Build a combined specification for filtering users based on multiple criteria
        Specification<User> spec = Specification.where(UserSpecification.hasTaskStatus(state))
                .and(UserSpecification.hasRole(role))
                .and(UserSpecification.isInLocation(location));

        // Execute the query with the combined specification and map results to ShortUserDto
        return userRepository.findAll(spec).stream()
                .map(DtoMapper::shortUserDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ShortUserDto> findUserWithProjectState(ProjectState projectState){
        Specification<User> specification = Specification.where(UserSpecification.findUsersWithProjectState(projectState));

        return userRepository.findAll(specification)
                .stream()
                .map(DtoMapper::shortUserDto)
                .collect(Collectors.toList());
    }
}
