package com.javaspring.lilurl.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class AliasTakenException extends RuntimeException
{
    public AliasTakenException(String message) {
        super(message);
    }

    public AliasTakenException(String message, Throwable cause){
        super(message, cause);
    }
}
