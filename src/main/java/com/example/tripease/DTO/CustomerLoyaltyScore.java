package com.example.tripease.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Customer’s Average Trip Distance>Overall Fleet Average Trip Distance
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CustomerLoyaltyScore {
    private int customer_id;
    private String first_name;
    private Double Average_trip_distance;
}
