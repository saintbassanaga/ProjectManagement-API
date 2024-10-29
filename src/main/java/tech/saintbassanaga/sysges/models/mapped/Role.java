package tech.saintbassanaga.sysges.models.mapped;

import jakarta.persistence.Enumerated;

/**
 * Created by saintbassanaga {stpaul}
 * In the Project SysGes at Sat - 10/26/24
 */

public enum Role {
    ADMIN("ROOT"),
    MEMBER("BELONG THE TEAM"),
    PROJECT_MANAGER("PROJECT TEAM LEAD ");

    Role(String name) {
    }
}
