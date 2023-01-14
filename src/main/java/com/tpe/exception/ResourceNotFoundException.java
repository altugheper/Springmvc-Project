package com.tpe.exception;

// Custom exception Class'i
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message){
        super(message);
    }

}
