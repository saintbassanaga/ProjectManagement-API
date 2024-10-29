package tech.saintbassanaga.sysges.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import tech.saintbassanaga.sysges.dtos.ShortUserDto;
import tech.saintbassanaga.sysges.models.User;
import tech.saintbassanaga.sysges.services.specificationService.UserSpecification;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    @Query("SELECT new tech.saintbassanaga.sysges.dtos.ShortUserDto(u.surname, u.username, u.email, u.role) FROM User u")
    List<ShortUserDto> findAllShortUsers();
}