package com.example.tripease.Transformers;

import com.example.tripease.DTO.Request.CabRequest;
import com.example.tripease.DTO.Response.CabResponse;
import com.example.tripease.Model.Cab;
import com.example.tripease.Model.Driver;

public class CabTransformer {
    public static Cab cabRequestToCab(CabRequest cabRequest){
        return Cab.builder()
                .cabNumber(cabRequest.getCabNumber())
                .cabModel(cabRequest.getCabModel())
                .perKmRate(cabRequest.getPerKmRate())
                .available(true)
                .build();
    }

    public static CabResponse cabToCabResponse(Cab cab, Driver driver){
        return CabResponse.builder()
                .cabNumber(cab.getCabNumber())
                .cabModel(cab.getCabModel())
                .perKmRate(cab.getPerKmRate())
                .available(cab.getAvailable())
                .driverResponse(DriverTransformer.driverToDriverResponse(driver))
                .build();
    }
}
