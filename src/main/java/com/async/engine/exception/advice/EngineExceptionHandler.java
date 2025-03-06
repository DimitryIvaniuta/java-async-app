package com.async.engine.exception.advice;

import com.async.engine.exception.EngineErrorResponse;
import com.async.engine.exception.EngineTaskException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class EngineExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<EngineErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        log.error("Unhandled exception", ex);  // Log full exception
        EngineErrorResponse errorResponse = new EngineErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setError("Internal Server Error");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EngineTaskException.class)
    public ResponseEntity<EngineErrorResponse> handleOrderCreateException(EngineTaskException ex, WebRequest request) {
        EngineErrorResponse errorResponse = new EngineErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setError("Thread error");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
