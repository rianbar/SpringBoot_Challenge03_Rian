package com.compassuol.msuser.exception;

import com.compassuol.msuser.exception.ExceptionType.BadGatewayException;
import com.compassuol.msuser.exception.ExceptionType.BusinessViolationException;
import com.compassuol.msuser.exception.ExceptionType.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> getPayloadValidationViolationException(MethodArgumentNotValidException ex) {
        String message = Objects.requireNonNull(ex.getFieldError()).getDefaultMessage();
        var response = new ExceptionResponsePayload(400, "BAD_REQUEST", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> getUnexpectedErrorException(RuntimeException ex) {
        String message = "occurred a unexpected error in server";
        var response = new ExceptionResponsePayload(500, "INTERNAL_SERVER_ERROR", message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(BusinessViolationException.class)
    public ResponseEntity<Object> getBusinessViolationException(BusinessViolationException ex) {
        String message = ex.getMessage();
        var response = new ExceptionResponsePayload(400, "BAD_REQUEST", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(BadGatewayException.class)
    public ResponseEntity<Object> getBadGatewayException(BadGatewayException ex) {
        String message = ex.getMessage();
        var response = new ExceptionResponsePayload(502, "BAD_GATEWAY", message);
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> getUserNotFoundException(UserNotFoundException ex) {
        String message = ex.getMessage();
        var response = new ExceptionResponsePayload(404, "NOT_FOUND", message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
