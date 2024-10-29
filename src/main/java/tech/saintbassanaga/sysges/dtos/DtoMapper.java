package tech.saintbassanaga.sysges.dtos;

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

    public static User creationUser(UserCreationDto userCreationDto){
        User newUser = new User();
        newUser.setName(userCreationDto.name());
        newUser.setSurname(userCreationDto.surname());
        newUser.setUsername(userCreationDto.username());
        newUser.setEmail(userCreationDto.email());
        newUser.setRole(userCreationDto.role());
        return newUser;
    }
}
