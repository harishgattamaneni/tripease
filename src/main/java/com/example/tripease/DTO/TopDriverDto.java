package com.example.tripease.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TopDriverDto {
    private String driverName;
    private Double totalEarnings;

    public TopDriverDto(String driverName, Double totalEarnings) {
        this.driverName = driverName;
        this.totalEarnings = totalEarnings;
    }
}
