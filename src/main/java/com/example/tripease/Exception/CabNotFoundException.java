package com.example.tripease.Exception;

public class CabNotFoundException extends RuntimeException {
    public CabNotFoundException(String message) {
        super(message);
    }
}
