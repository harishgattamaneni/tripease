package com.example.tripease.Service;

import com.example.tripease.DTO.Request.CabRequest;
import com.example.tripease.DTO.Response.CabResponse;
import com.example.tripease.Exception.DriverNotFoundException;
import com.example.tripease.Model.Cab;
import com.example.tripease.Model.Driver;
import com.example.tripease.Repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import static com.example.tripease.Transformers.CabTransformer.cabRequestToCab;
import static com.example.tripease.Transformers.CabTransformer.cabToCabResponse;

@Service
public class CabService {
    @Autowired
    DriverRepository driverRepository;

    public CabResponse registerCab(CabRequest cabRequest, int driverId) {
        Optional<Driver> optionalDriver = driverRepository.findById(driverId);
        if(optionalDriver.isEmpty()){
            throw new DriverNotFoundException("Invalid Driver Id");
        }

        Driver driver = optionalDriver.get();
        Cab cab = cabRequestToCab(cabRequest);
        driver.setCab(cab);
        Driver savedDriver = driverRepository.save(driver);//this will save both driver and cab
        return cabToCabResponse(savedDriver.getCab(),savedDriver);
    }
}
