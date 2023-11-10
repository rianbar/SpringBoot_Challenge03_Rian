package com.compassuol.msnotification.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponsePayload {
    private int code;
    private String status;
    private String message;
}
