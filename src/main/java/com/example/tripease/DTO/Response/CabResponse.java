package com.example.tripease.DTO.Response;

import lombok.*;

@NoArgsConstructor //default constructor - LOMBO
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CabResponse {
    private String cabNumber;
    private String cabModel;
    private int perKmRate;
    private Boolean available;
    private DriverResponse driverResponse;
}
