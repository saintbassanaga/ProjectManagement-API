package tech.saintbassanaga.sysges.services.specificationService;

import jakarta.persistence.criteria.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import tech.saintbassanaga.sysges.models.Project;
import tech.saintbassanaga.sysges.models.Task;
import tech.saintbassanaga.sysges.models.User;
import tech.saintbassanaga.sysges.models.mapped.ProjectState;

/**
 * Created by saintbassanaga
 * In the Project SysGes at Mon - 10/28/24
 */

@AllArgsConstructor
@Repository
public class UserSpecification {

    public static Specification<User> hasTaskStatus(String taskStatus) {
        return (root, query, criteriaBuilder) -> {
            Join<User, Task> taskJoin = root.join("tasks");
            return criteriaBuilder.equal(taskJoin.get("state"), taskStatus);
        };
    }

    public static Specification<User> hasRole(String role) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("role"), role);
    }

    public static Specification<User> isInLocation(String location) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("location"), location);
    }

    public static Specification<User> findUsersWithProjectState(ProjectState projectState) {

        return ((root, query, criteriaBuilder) -> {
            Join<User, Project> projectJoin = root.join("projects", JoinType.INNER);
            return criteriaBuilder.equal(projectJoin.get("projectState"), projectState);
        });
    }
}
