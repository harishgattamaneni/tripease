package com.example.tripease.Service;

import com.example.tripease.DTO.PopularCabModelDto;
import com.example.tripease.DTO.RateRangeDto;
import com.example.tripease.DTO.Request.CabRequest;
import com.example.tripease.DTO.Response.CabResponse;
import com.example.tripease.Exception.DriverNotFoundException;
import com.example.tripease.Exception.DriverAlreadyAssignedACab;
import com.example.tripease.Model.Cab;
import com.example.tripease.Model.Driver;
import com.example.tripease.Repository.CabRepository;
import com.example.tripease.Repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static com.example.tripease.Transformers.CabTransformer.cabRequestToCab;
import static com.example.tripease.Transformers.CabTransformer.cabToCabResponse;

@Service
public class CabService {
    @Autowired
    DriverRepository driverRepository;

    @Autowired
    CabRepository cabRepository;

    public CabResponse registerCab(CabRequest cabRequest, int driverId) {
        Optional<Driver> optionalDriver = driverRepository.findById(driverId);
        if(optionalDriver.isEmpty()){
            throw new DriverNotFoundException("Invalid Driver Id");
        }

        Driver driver = optionalDriver.get();
        if(driver.getCab()!=null){
            throw new DriverAlreadyAssignedACab("Driver has already been assigned a cab earlier");
        }
        Cab cab = cabRequestToCab(cabRequest);
        driver.setCab(cab);
        Driver savedDriver = driverRepository.save(driver);//this will save both driver and cab
        return cabToCabResponse(savedDriver.getCab(),savedDriver);
    }

    public PopularCabModelDto getPopularCarModel(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -days);
        Date pastDate = cal.getTime();
        return cabRepository.getPopularCarModel(pastDate);
    }

    public List<RateRangeDto> getRateRange(int minRate, int maxRate) {
        return cabRepository.getRateRange(minRate,maxRate);
    }
}
