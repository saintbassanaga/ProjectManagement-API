package tech.saintbassanaga.sysges.dtos;

import tech.saintbassanaga.sysges.models.Comment;
import tech.saintbassanaga.sysges.models.Project;
import tech.saintbassanaga.sysges.models.Task;
import tech.saintbassanaga.sysges.models.User;
import tech.saintbassanaga.sysges.models.mapped.CommentType;

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
                                null,
                                task.getTitle(),
                                task.getDueDate(),
                                task.getState(), task.getDescription()))
                        .collect(Collectors.toSet()));
    }


    public static Comment commentDto(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setType(commentDto.type());
        comment.setContent(commentDto.content());

        return comment;
    }

    public static ShortUserDto shortUserDto(User user){
        return new ShortUserDto(
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getRole()
        );
    }

    public static Task fromTaskDto(TaskDto taskDto) {
        Task task = new Task();
        task.setTitle(taskDto.title());
        task.setDescription(taskDto.description());
        task.setState(taskDto.state());
        task.setDueDate(taskDto.dueDate());
        return task;
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
    public static TaskDto toTaskDto(Task project){
        return  new TaskDto(
                null,
                project.getDescription(),
                project.getDueDate(),
                project.getState(),
                project.getDescription());
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

/*
*    public static Project updateProject(UpdateProjectDto updateProjectDto){

    }*/

}
