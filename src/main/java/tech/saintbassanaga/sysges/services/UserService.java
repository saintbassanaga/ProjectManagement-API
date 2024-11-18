package tech.saintbassanaga.sysges.services;

import org.springframework.stereotype.Component;
import tech.saintbassanaga.sysges.dtos.ShortUserDto;
import tech.saintbassanaga.sysges.dtos.ShowUserDto;
import tech.saintbassanaga.sysges.dtos.UpdateUserDto;
import tech.saintbassanaga.sysges.dtos.UserCreationDto;
import tech.saintbassanaga.sysges.models.mapped.ProjectState;

import java.util.List;
import java.util.UUID;

/**
 * Created by saintbassanaga {stpaul}
 * In the Project SysGes at Sat - 10/26/24
 */
@Component
public interface UserService {
    String createUser(UserCreationDto user);

    ShowUserDto update(UUID userUuid, UpdateUserDto updateUserDto);

    List<ShortUserDto> findAll();

    ShowUserDto findByUuid(UUID uuid);

    List<ShortUserDto> findUserWithTaskRunning(String status, String role, String location);

    List<ShortUserDto> findUserWithProjectState(ProjectState projectState);

    String changeUserRole(UUID userUuid, String role);
}
