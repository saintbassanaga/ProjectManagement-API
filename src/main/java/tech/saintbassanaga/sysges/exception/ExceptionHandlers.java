package tech.saintbassanaga.sysges.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;

/**
 * Created by saintbassanaga {saintbassanaga}
 * In the Project StockHubAPI at Thu - 9/19/24
 */

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(GeneralException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleException(GeneralException ex) {
        return new ResponseEntity<>(new ErrorResponse(), HttpStatus.BAD_REQUEST);
    }
}
