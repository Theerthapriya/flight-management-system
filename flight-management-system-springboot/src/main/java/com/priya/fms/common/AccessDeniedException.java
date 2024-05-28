package com.priya.fms.common;

public class AccessDeniedException extends RuntimeException{
    
    public AccessDeniedException(String message){
        super(message);
    }
    
}
