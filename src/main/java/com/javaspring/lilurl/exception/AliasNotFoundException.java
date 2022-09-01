package com.javaspring.lilurl.exception;

public class AliasNotFoundException extends RuntimeException{

    public AliasNotFoundException(String message){
        super(message);
    }

    public AliasNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
