package com.hamitmizrak.utils;

import java.util.Objects;

public class NotFoundException extends RuntimeException{

    private NotFoundException(String msg){
        super(msg);
    }

    public static NotFoundException of(String type, Object id){
        return new NotFoundException(type + " " + id);
    }

}
