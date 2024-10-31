package tech.saintbassanaga.sysges.exception;

import lombok.Getter;
import org.springframework.lang.Nullable;

/**
 * Created by saintbassanaga {saintbassanaga}
 * In the Project StockHubAPI at Thu - 9/19/24
 */

@Getter
public class GeneralException extends RuntimeException {
    private final ErrorCode errorCode;
    private final StatusCode errorStatus;
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     * @param errorStatus the statute Error : involve HttpStatus message
     * @param errorCode the Error Code : the errorCode is used to brake the HttpErrorCode limits
     */
    public GeneralException(String message, @Nullable ErrorCode errorCode, @Nullable StatusCode errorStatus) {
        super(message);
        this.errorCode = errorCode;
        this.errorStatus = errorStatus;
    }
}
