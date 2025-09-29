package com.example.tripease.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor //default constructor - LOMBOK
@AllArgsConstructor
@Getter
@Setter
public class CabRequest {
    private String cabNumber;
    private String cabModel;
    private int perKmRate;
}
