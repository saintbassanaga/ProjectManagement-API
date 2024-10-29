package tech.saintbassanaga.sysges.services;

import org.springframework.stereotype.Component;
import tech.saintbassanaga.sysges.dtos.*;
import tech.saintbassanaga.sysges.models.Project;

import java.util.List;
import java.util.UUID;

/**
 * Created by saintbassanaga {stpaul}
 * In the Project SysGes at Mon - 10/28/24
 */

@Component
public interface ProjectService {
    public String createProject(CreateProjectDto createProjectDto);
    public Project update(UUID userUuid, UpdateProjectDto updateProjectDto);
    public List<ShortProjectDto> findAll();
    public ShowProjectDto findByUuid(UUID uuid);
    public void deleteUser(UUID uuid);
}
