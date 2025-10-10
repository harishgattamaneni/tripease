package com.example.tripease.Exception;

public class TripCannotBeCancelled extends RuntimeException {
    public TripCannotBeCancelled(String message) {
        super(message);
    }
}
