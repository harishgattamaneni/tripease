package com.example.tripease.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor //default constructor - LOMBO
@AllArgsConstructor
@Getter
@Setter
public class BookingRequest {
    private String pickup;
    private String destination;
    private double tripDistanceInKm;
}
