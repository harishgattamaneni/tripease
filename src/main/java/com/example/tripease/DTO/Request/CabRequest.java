package com.example.tripease.DTO.Request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor //default constructor - LOMBOK
@AllArgsConstructor
@Getter
@Setter
public class CabRequest {
    @NotBlank(message = "Please give a cabNumber")
    private String cabNumber;

    @NotBlank(message = "Please give a cabModel")
    private String cabModel;

    @NotNull(message = "Please give a cabModel")
    @Min(value = 30, message = "perKmRate must be at least 18")
    @Max(value = 80, message = "perKmRate cannot exceed 80")
    private int perKmRate;
}
