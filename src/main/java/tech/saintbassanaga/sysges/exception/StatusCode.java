package tech.saintbassanaga.sysges.exception;

import lombok.Getter;

/**
 * Created by saintbassanaga {saintbassanaga}
 * In the Project StockHubAPI at Thu - 9/19/24
 */

@Getter
public enum StatusCode {
    BAD_REQUEST("400"),
    UNAUTHORIZED_ACCESS("401"),
    RESOURCE_NOT_FOUND("404"),
    INTERNAL_SERVER_ERROR("500"),
    OK("200"),
    ACCEPTED("201"),
    FORBIDDEN("403");

    StatusCode(String s) {

    }
}
