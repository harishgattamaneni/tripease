package com.example.tripease.DTO.Response;

import com.example.tripease.Enum.TripStatus;
import lombok.*;
import java.util.Date;

@NoArgsConstructor //default constructor - LOMBO
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BookingResponse {
    private String pickup;
    private String destination;
    private double tripDistanceInKm;
    private TripStatus tripStatus;
    private double billAmount;
    private Date bookedAt;
    private Date lastUpdatedAt;

    private CustomerResponse customerResponse;
    private CabResponse cabResponse;
}
