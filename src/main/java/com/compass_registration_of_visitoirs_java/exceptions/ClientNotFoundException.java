package com.compass_registration_of_visitoirs_java.exceptions;

public class ClientNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public ClientNotFoundException(String message) {
        super(message);
    }
}
