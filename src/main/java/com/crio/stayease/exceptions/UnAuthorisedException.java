package com.crio.stayease.exceptions;

public class UnAuthorisedException extends RuntimeException{
    public UnAuthorisedException(String message) {
        super(message);
    }
}
