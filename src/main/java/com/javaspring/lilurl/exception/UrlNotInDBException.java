package com.javaspring.lilurl.exception;

import net.bytebuddy.implementation.bytecode.Throw;

public class UrlNotInDBException extends RuntimeException {
    public UrlNotInDBException(String message) {
        super(message);
    }

    public UrlNotInDBException(String message, Throwable cause){
        super(message, cause);
    }
}
