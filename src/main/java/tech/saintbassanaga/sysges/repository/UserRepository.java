package tech.saintbassanaga.sysges.repository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import tech.saintbassanaga.sysges.dtos.ShortUserDto;
import tech.saintbassanaga.sysges.models.User;
import tech.saintbassanaga.sysges.services.specificationService.UserSpecification;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    @Query("SELECT new tech.saintbassanaga.sysges.dtos.ShortUserDto(u.surname, u.username, u.email, u.role) FROM User u")
    List<ShortUserDto> findAllShortUsers();

    Optional<User> findByUsername(String username);

    boolean existsByUsername(@NotNull @Size(min = 6, max = 15) String username);

    @Query("select (count(u) > 0) from User u where upper(u.email) like upper(?1)")
    boolean existsByEmail(@NonNull String email);
}