package com.example.tripease.Exception;

public class tripAlreadyEnded extends RuntimeException {
    public tripAlreadyEnded(String message) {
        super(message);
    }
}
