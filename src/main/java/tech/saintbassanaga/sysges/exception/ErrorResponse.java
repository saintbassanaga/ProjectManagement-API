package tech.saintbassanaga.sysges.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by saintbassanaga {saintbassanaga}
 * In the Project StockHubAPI at Thu - 9/19/24
 */
@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {

    private String message;
    private int statusCode;
    private long timestamp;
    private ErrorCode errorCode;
    private StatusCode errorDescription;
    private List<String> validationErrors;

    public ErrorResponse(String message, int statusCode, long timestamp, ErrorCode errorCode,
                         StatusCode errorDescription, List<String> validationErrors) {
        this.message = message;
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.validationErrors = validationErrors;
    }
}
