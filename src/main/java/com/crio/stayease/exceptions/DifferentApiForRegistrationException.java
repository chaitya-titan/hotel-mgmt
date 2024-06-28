package com.crio.stayease.exceptions;

public class DifferentApiForRegistrationException extends RuntimeException {
    public DifferentApiForRegistrationException(String message) {
        super(message);
    }
}
