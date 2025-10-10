package com.example.tripease.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BestMatchDto {
    private int driver_id;
    private String driver_name;
    private int per_km_rate;
    private double total_distance_by_driver;
}
