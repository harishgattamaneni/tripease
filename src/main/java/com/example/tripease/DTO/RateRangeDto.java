package com.example.tripease.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RateRangeDto {
    private String cabModel;
    private String cabNumber;
    private int perKmRate;
    private String driverName;
    private int driverAge;

}
