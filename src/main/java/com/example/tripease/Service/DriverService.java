package com.example.tripease.Service;

import com.example.tripease.DTO.Request.DriverRequest;
import com.example.tripease.DTO.Response.DriverResponse;
import com.example.tripease.Model.Driver;
import com.example.tripease.Repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.example.tripease.Transformers.DriverTransformer.driverRequestToDriver;
import static com.example.tripease.Transformers.DriverTransformer.driverToDriverResponse;


@Service
public class DriverService {
    @Autowired
    DriverRepository driverRepository;


    public DriverResponse addDriver(DriverRequest driverRequest) {
        Driver driver = driverRequestToDriver(driverRequest);
        return driverToDriverResponse(driverRepository.save(driver));
    }
}
