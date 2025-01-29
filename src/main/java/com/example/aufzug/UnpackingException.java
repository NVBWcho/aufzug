package com.example.aufzug;

public class UnpackingException extends RuntimeException {

    public UnpackingException(){
        super("Non conforming data types");
    }
}
