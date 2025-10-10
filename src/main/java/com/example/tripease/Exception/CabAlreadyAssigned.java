package com.example.tripease.Exception;

public class CabAlreadyAssigned extends RuntimeException {
    public CabAlreadyAssigned(String message) {
        super(message);
    }
}
