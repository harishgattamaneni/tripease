package com.example.tripease.DTO.Request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor //default constructor - LOMBO
@AllArgsConstructor
@Getter
@Setter
public class BookingRequest {

    @NotBlank(message = "Please give a pickup location")
    private String pickup;

    @NotBlank(message = "Please give a destination")
    private String destination;

    @NotNull(message = "Please give a trip distance")
    @Min(value = 1, message = "Trip distance must be at least 1")
    @Max(value = 100, message = "Trip distance cannot exceed 100")
    private double tripDistanceInKm;
}
