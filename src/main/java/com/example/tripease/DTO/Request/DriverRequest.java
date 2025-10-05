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
public class DriverRequest {
    @NotBlank(message = "Please give a name")
    private String name;

    @NotNull(message = "Please give an age")
    @Min(value = 18, message = "Entered Age must be at least 18")
    @Max(value = 60, message = "Entered Age cannot exceed 60")
    private int age;

    @NotBlank(message = "Please give a Email")
    @Email(message = "Entered Email Id is not valid")
    private String emailId;
}
