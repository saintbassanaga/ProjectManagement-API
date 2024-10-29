package tech.saintbassanaga.sysges.dtos;

import tech.saintbassanaga.sysges.models.Project;
import tech.saintbassanaga.sysges.models.User;

import java.util.stream.Collectors;

/**
 * Created by saintbassanaga {stpaul}
 * In the Project SysGes at Tue - 10/29/24
 */
public class DtoMapper {
    public static ShowUserDto showUserDto(User user){
        return new ShowUserDto(user.getSurname(),
        user.getUsername(),
                user.getEmail(),
                user.getEmail(),
                user.getRole(),
                user.getTasks()
                        .stream()
                        .map(task -> new TaskDto(
                                task.getProject().getTitre(),
                                task.getTitle(),
                                task.getDueDate(),
                                task.getState()))
                        .collect(Collectors.toSet()));
    }

    public static ShortUserDto shortUserDto(User user){
        return new ShortUserDto(
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getRole()
        );
    }

    public static User creationUser(UserCreationDto updateProjectDto){
        User newUser = new User();
        newUser.setName(updateProjectDto.name());
        newUser.setSurname(updateProjectDto.surname());
        newUser.setUsername(updateProjectDto.username());
        newUser.setEmail(updateProjectDto.email());
        newUser.setRole(updateProjectDto.role());
        return newUser;
    }

    public static ShowProjectDto showProjectDto(Project project){
        return  new ShowProjectDto(
                project.getTitre(),
                project.getDescription(),
                project.getStartDate(),
                project.getEndDate(),
                project.getProjectState(),
                project.getUsers()
                        .stream()
                        .map(
                                user -> new ShortUserDto(user.getSurname(),
                                        user.getUsername(),user.getEmail(),
                                        user.getRole()))
                        .collect(Collectors.toSet())
        );
    }

    public static Project creationProject(CreateProjectDto createProjectDto){
        Project newProject = new Project();
        newProject.setTitre(createProjectDto.titre());
        newProject.setDescription(createProjectDto.description());
        newProject.setStartDate(createProjectDto.startDate());
        newProject.setEndDate(createProjectDto.endDate());
        newProject.setProjectState(createProjectDto.projectState());
        return newProject;
    }

/*    public static Project updateProject(UpdateProjectDto updateProjectDto){

    }*/

}
