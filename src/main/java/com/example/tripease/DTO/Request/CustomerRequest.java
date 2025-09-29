package com.example.tripease.DTO.Request;

import com.example.tripease.Enum.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor //default constructor - LOMBO
@AllArgsConstructor
@Getter
@Setter
public class CustomerRequest {
    private String name;
    private int age;
    private String emailId;
    private Gender gender;
}
