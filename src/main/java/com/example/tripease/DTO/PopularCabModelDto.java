package com.example.tripease.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PopularCabModelDto {
    private String CabModel;
    private Double totalTripDistance;
}
