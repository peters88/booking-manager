package com.psayol.bookingmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnableToProcessRequestException extends RuntimeException{
    public UnableToProcessRequestException(String exception){
        super(exception);
    }
}
