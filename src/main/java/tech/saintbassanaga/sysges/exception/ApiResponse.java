package tech.saintbassanaga.sysges.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by saintbassanaga {stpaul}
 * In the Project SysGes at Wed - 10/30/24
 */
// Inner class to format API responses

@Getter
@Setter
public class ApiResponse {
    private String message;
    private int status;
    private Object data;

    public ApiResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public ApiResponse(String message, int status, Object data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }
}