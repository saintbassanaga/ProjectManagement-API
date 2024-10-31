package tech.saintbassanaga.sysges.exception;

/**
 * Created by saintbassanaga {stpaul}
 * In the Project SysGes at Wed - 10/30/24
 */
public enum ErrorCode {
    BAD_REQUEST("400"),
    UNAUTHORIZED_ACCESS("401"),
    RESOURCE_NOT_FOUND("404"),
    INTERNAL_SERVER_ERROR("500"),
    FORBIDDEN("403");

    ErrorCode(String s) {

    }
}
