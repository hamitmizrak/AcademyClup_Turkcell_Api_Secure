package com.hamitmizrak.exceptions;

public class NotFoundException extends RuntimeException{

    private NotFoundException(String msg){
        super(msg);
    }

    public static NotFoundException of(String type, Object id){
        return new NotFoundException(type + " " + id);
    }

}
