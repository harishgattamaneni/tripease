package com.example.tripease.Exception;

public class NoNearestBooking extends RuntimeException {
    public NoNearestBooking(String message) {
        super(message);
    }
}
