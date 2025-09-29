package com.example.tripease.DTO.Response;

import lombok.*;

@NoArgsConstructor //default constructor - LOMBO
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DriverResponse {

    private int driverId;
    private String name;
    private int age;
    private String emailId;
}
