package tech.saintbassanaga.sysges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import tech.saintbassanaga.sysges.dtos.ShortProjectDto;
import tech.saintbassanaga.sysges.models.Project;

import java.util.List;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID>, JpaSpecificationExecutor<Project> {
    @Query("SELECT new tech.saintbassanaga.sysges.dtos.ShortProjectDto(u.titre, u.description, u.startDate, u.endDate,u.projectState) FROM Project u")
    List<ShortProjectDto> findAllProjectUsers();
}