package tech.saintbassanaga.sysges.services.specificationService;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tech.saintbassanaga.sysges.models.Project;
import tech.saintbassanaga.sysges.models.Task;
import tech.saintbassanaga.sysges.models.User;
import tech.saintbassanaga.sysges.models.mapped.ProjectState;
import tech.saintbassanaga.sysges.models.mapped.TaskState;

import java.util.UUID;


@Service
/**
 * Created by saintbassanaga {stpaul}
 * In the Project SysGes at Sat - 10/26/24
 */
public class TaskSearchService{
    public static Specification<Task> completedBy(User userUuid) {
        return (root, query, criteriaBuilder) -> {
            Join<Task, User> taskJoin = root.join("users");
            return criteriaBuilder.equal(taskJoin.get("user"), userUuid);
        };
    }

    public static Specification<Task> findTaskWithState(TaskState taskState) {

        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("task"), taskState);
        });
    }

    public Specification<Task> findTaskByProjectTaskUuid(UUID projectUuid, UUID taskUuid) {
        return (root, query, criteriaBuilder) -> {
            Join<Task , Project> joinTask = root.join("project", JoinType.INNER);
            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("uuid"),taskUuid),
                    criteriaBuilder.equal(joinTask.get("project"),projectUuid)
            );
        };
    }
}
