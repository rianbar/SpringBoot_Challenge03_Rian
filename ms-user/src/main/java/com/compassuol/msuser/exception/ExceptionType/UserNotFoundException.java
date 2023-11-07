package com.compassuol.msuser.exception.ExceptionType;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
