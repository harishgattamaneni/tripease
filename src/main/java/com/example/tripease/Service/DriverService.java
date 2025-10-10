package com.example.tripease.Service;

import com.example.tripease.DTO.BestMatchDto;
import com.example.tripease.DTO.Request.DriverRequest;
import com.example.tripease.DTO.Response.DriverResponse;
import com.example.tripease.DTO.TopDriverDto;
import com.example.tripease.Exception.CabNotFoundException;
import com.example.tripease.Exception.DriverNotFoundException;
import com.example.tripease.Model.Booking;
import com.example.tripease.Model.Cab;
import com.example.tripease.Model.Driver;
import com.example.tripease.Repository.BookingRepository;
import com.example.tripease.Repository.CabRepository;
import com.example.tripease.Repository.DriverRepository;
import com.example.tripease.Transformers.DriverTransformer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import com.example.tripease.Exception.CabAlreadyAssigned;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.example.tripease.Transformers.DriverTransformer.driverRequestToDriver;
import static com.example.tripease.Transformers.DriverTransformer.driverToDriverResponse;


@Service
public class DriverService {
    @Autowired
    DriverRepository driverRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    CabRepository cabRepository;

    public DriverResponse addDriver(DriverRequest driverRequest) {
        Driver driver = driverRequestToDriver(driverRequest);
        return driverToDriverResponse(driverRepository.save(driver));
    }

    public double getTotalAmount(int driverid, Date date1, Date date2) {
        List<Booking> li=bookingRepository.findBookingsByDriverId(driverid,date1,date2);
        double totalAmount=0;
        for (Booking booking : li) {
            totalAmount += booking.getBillAmount();
        }
        return totalAmount;
    }

    public List<TopDriverDto> getTopEarnersNative(int topN, int daysInPast) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -daysInPast);
        Date pastDate = cal.getTime();

        // 2. Call the native query
        return driverRepository.findTopDriversNative(pastDate, topN);
    }

    @Transactional
    public String swapCab(int driverId, int newCabId) {
        Optional<Cab> optionalCab=cabRepository.findById(newCabId);
        if(optionalCab.isEmpty()){
            throw new CabNotFoundException("there is no cab with given id");
        }
        Optional<Driver> optionalDriver=driverRepository.findByCabCabId(newCabId);
        if(optionalDriver.isPresent()){
            throw new CabAlreadyAssigned("the given cab id is already in use by one of the drivers");
        }
        Optional<Driver> currentDriver=driverRepository.findById(driverId);
        if(currentDriver.isEmpty()){
            throw new DriverNotFoundException("Invalid driverId");
        }
        currentDriver.get().setCab(optionalCab.get());
        return "cab is successfully assigned to the driver";
    }

    public BestMatchDto bestMatch() {
        return driverRepository.bestMatch();
    }
}
