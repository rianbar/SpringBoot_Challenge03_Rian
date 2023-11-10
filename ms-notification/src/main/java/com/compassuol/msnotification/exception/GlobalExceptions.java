package com.compassuol.msnotification.exception;

import com.compassuol.msnotification.exception.type.TransferObjectException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptions {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> getUnexpectedErrorException(RuntimeException ex) {
        String message = ex.getMessage();
        var response = new ExceptionResponsePayload(500, "INTERNAL_SERVER_ERROR", message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(TransferObjectException.class)
    public ResponseEntity<Object> getTransferObjectException(TransferObjectException ex) {
        String message = ex.getMessage();
        var response = new ExceptionResponsePayload(400, "BAD_REQUEST", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
